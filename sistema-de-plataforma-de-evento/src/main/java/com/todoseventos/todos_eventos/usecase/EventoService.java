package com.todoseventos.todos_eventos.usecase;

import com.todoseventos.todos_eventos.dao.*;
import com.todoseventos.todos_eventos.dto.requestDTO.EventoRequestDTO;
import com.todoseventos.todos_eventos.dto.responseDTO.CepResponseDTO;
import com.todoseventos.todos_eventos.dto.responseDTO.EventoResponseDTO;
import com.todoseventos.todos_eventos.enuns.CategoriaEnum;
import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.gateway.CepService;
import com.todoseventos.todos_eventos.gateway.EmailService;
import com.todoseventos.todos_eventos.model.evento.Categoria;
import com.todoseventos.todos_eventos.model.evento.Endereco;
import com.todoseventos.todos_eventos.model.evento.Evento;
import com.todoseventos.todos_eventos.utils.Validacoes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EventoService {


    @Autowired
    private IEventoJdbcTemplateDAO IEventoJdbcTemplateDAO;

    @Autowired
    private Validacoes validacoes;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CepService cepService;

    @Autowired
    private IEnderecoJdbcTemplateDAO IEnderecoJdbcTemplateDAO;

    @Autowired
    private IClienteFisicaJdbcTemplateDAO IClienteFisicaJdbcTemplateDAO;

    @Autowired
    private IClienteJuridicaJdbcTemplateDAO IClienteJuridicaJdbcTemplateDAO;

    @Autowired
    private ICategoriaJdbcTemplateDAO ICategoriaJdbcTemplateDAO;

    /**
     * Cadastra um novo evento.
     *
     * @param eventoRequestDTO Objeto contendo os detalhes do evento a ser cadastrado.
     * @return Um objeto de resposta contendo os detalhes do evento cadastrado.
     */
    public EventoResponseDTO cadastrarNovoEvento(EventoRequestDTO eventoRequestDTO) {

        if (eventoRequestDTO.getCategoria() == null) {
            throw new CustomException(ExceptionMessages.TIPO_CATEGORIA_INVALIDO);
        }
        Categoria categoria = ICategoriaJdbcTemplateDAO.buscarNomeCategoria(eventoRequestDTO.getCategoria().name());

        if (Objects.isNull(categoria)) {
            throw new CustomException(ExceptionMessages.CATEGORIA_INVALIDA);
        }

        if (!validacoes.validarCep(eventoRequestDTO.getCep())) {
            throw new CustomException(ExceptionMessages.CEP_INVALIDO);
        }

        CepResponseDTO cepResponseDTO = cepService.consultarCep(eventoRequestDTO.getCep());
        eventoRequestDTO.setRua(cepResponseDTO.getLogradouro());
        eventoRequestDTO.setBairro(cepResponseDTO.getBairro());
        eventoRequestDTO.setCidade(cepResponseDTO.getLocalidade());
        eventoRequestDTO.setUf(cepResponseDTO.getUf());

        Evento evento = Evento.builder()
                .nome_evento(eventoRequestDTO.getNome_evento())
                .dataHora_evento(eventoRequestDTO.getDataHora_evento())
                .dataHora_eventofinal(eventoRequestDTO.getDataHora_eventofinal())
                .descricao(eventoRequestDTO.getDescricao())
                .status("ATIVO")
                .id_categoria(categoria.getIdCategoria())
                .valorIngresso(eventoRequestDTO.getValorIngresso())
                .limitePessoas(eventoRequestDTO.getLimitePessoas())
                .build();

        Evento eventoSalvo = IEventoJdbcTemplateDAO.salvarEvento(evento);

        Endereco endereco = Endereco.builder()
                .idEvento(eventoSalvo.getIdEvento())
                .rua(eventoRequestDTO.getRua())
                .numero(eventoRequestDTO.getNumero())
                .bairro(eventoRequestDTO.getBairro())
                .cidade(eventoRequestDTO.getCidade())
                .cep(eventoRequestDTO.getCep())
                .uf(eventoRequestDTO.getUf())
                .build();

        Endereco enderecoSalvo = IEnderecoJdbcTemplateDAO.salverEndereco(endereco);

        return mapearEvento(categoria, eventoSalvo, enderecoSalvo);
    }

    /**
     * Encerra um evento.
     *
     * @param idEvento O ID do evento a ser encerrado.
     * @return Um objeto de resposta contendo os detalhes do evento encerrado.
     */
    public EventoResponseDTO encerrarEvento(Integer idEvento) {

        Evento evento = IEventoJdbcTemplateDAO.procurarPorId(idEvento)
                .orElseThrow(() -> new CustomException(ExceptionMessages.EVENTO_NAO_ENCONTRADO));
        evento.setStatus("CANCELADO");
        Evento updatedEvento = IEventoJdbcTemplateDAO.atualizarEvento(evento);

        //todo recurso de reembolso

        return mapearEncerramentoEvento(evento);
    }

    /**
     * Mapeia os detalhes do encerramento de um evento para um objeto de resposta.
     *
     * @param evento O objeto evento contendo os detalhes do evento.
     * @return Um objeto de resposta contendo os detalhes do evento encerrado.
     */
    private EventoResponseDTO mapearEncerramentoEvento(Evento evento) {

        Categoria categoria = ICategoriaJdbcTemplateDAO.procurarId(evento.getId_categoria());
        Endereco endereco = IEnderecoJdbcTemplateDAO.procurarPorIdEvento(evento.getIdEvento())
                .orElseThrow(() -> new CustomException(ExceptionMessages.ENDERECO_NAO_ENCONTRADO + evento.getNome_evento()));

        return EventoResponseDTO.builder()
                .idEvento(evento.getIdEvento())
                .nome_evento(evento.getNome_evento())
                .dataHora_evento(evento.getDataHora_evento())
                .dataHora_eventofinal(evento.getDataHora_eventofinal())
                .descricao(evento.getDescricao())
                .status(evento.getStatus())
                .categoria(CategoriaEnum.valueOf(categoria.getNomeCategoria()))
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .cep(endereco.getCep())
                .uf(endereco.getUf())
                .build();
    }

    /**
     * Mapeia os detalhes de um evento para um objeto de resposta.
     *
     * @param categoria     O objeto categoria contendo os detalhes da categoria do evento.
     * @param eventoSalvo   O objeto evento contendo os detalhes do evento salvo.
     * @param enderecoSalvo O objeto endereço contendo os detalhes do endereço salvo.
     * @return Um objeto de resposta contendo os detalhes do evento.
     */
    private EventoResponseDTO mapearEvento(Categoria categoria, Evento eventoSalvo, Endereco enderecoSalvo) {
        return EventoResponseDTO.builder()
                .idEvento(eventoSalvo.getIdEvento())
                .nome_evento(eventoSalvo.getNome_evento())
                .dataHora_evento(eventoSalvo.getDataHora_evento())
                .dataHora_eventofinal(eventoSalvo.getDataHora_eventofinal())
                .descricao(eventoSalvo.getDescricao())
                .status(eventoSalvo.getStatus())
                .categoria(CategoriaEnum.valueOf(categoria.getNomeCategoria()))
                .rua(enderecoSalvo.getRua())
                .numero(enderecoSalvo.getNumero())
                .bairro(enderecoSalvo.getBairro())
                .cidade(enderecoSalvo.getCidade())
                .cep(enderecoSalvo.getCep())
                .uf(enderecoSalvo.getUf())
                .valorIngresso(eventoSalvo.getValorIngresso())
                .limitePessoas(eventoSalvo.getLimitePessoas())
                .build();
    }


    /**
     * Localiza todos os eventos cadastrados.
     *
     * @return Uma lista de objetos de resposta contendo os detalhes dos eventos localizados.
     */
    public List<EventoResponseDTO> listarEventos() {

        List<Evento> eventoList;
        List<EventoResponseDTO> eventoResponseDTOList = new ArrayList<>();

        try {
            eventoList = IEventoJdbcTemplateDAO.listarEvento();
        } catch (Exception e) {
            throw new CustomException(ExceptionMessages.ERRO_BUSCAR_EVENTOS + e.getMessage());
        }

        for (Evento evento : eventoList) {
            Categoria categoria;
            Endereco endereco;

            try {
                categoria = ICategoriaJdbcTemplateDAO.procurarId(evento.getId_categoria());
            } catch (Exception e) {
                throw new CustomException(ExceptionMessages.ERRO_BUSCAR_CATEGORIA_EVENTO + e.getMessage());
            }

            try {
                endereco = IEnderecoJdbcTemplateDAO.procurarPorIdEvento(evento.getIdEvento())
                        .orElseThrow(() -> new CustomException(ExceptionMessages.ENDERECO_NAO_ENCONTRADO + evento.getNome_evento()));
            } catch (Exception e) {
                throw new CustomException(ExceptionMessages.ERRO_BUSCAR_ENDERECO_EVENTO + e.getMessage());
            }

            EventoResponseDTO eventoResponseDTO = mapearEvento(categoria, evento, endereco);
            eventoResponseDTOList.add(eventoResponseDTO);
        }
        return eventoResponseDTOList;
    }

    /**
     * Procura um evento pelo nome.
     *
     * @param nomeEvento O nome do evento a ser procurado.
     * @return Um objeto de resposta contendo os detalhes do evento localizado.
     */
    public EventoResponseDTO procurarEventoPorNome(String nomeEvento) {

        Evento evento = IEventoJdbcTemplateDAO.procurarPorNome(nomeEvento)
                .orElseThrow(() -> new CustomException(ExceptionMessages.EVENTO_NAO_ENCONTRADO));

        Categoria categoria = ICategoriaJdbcTemplateDAO.procurarId(evento.getId_categoria());
        Endereco endereco = IEnderecoJdbcTemplateDAO.procurarPorIdEvento(evento.getIdEvento())
                .orElseThrow(() -> new CustomException(ExceptionMessages.ENDERECO_NAO_ENCONTRADO + nomeEvento));

        return mapearEvento(categoria, evento, endereco);
    }

    /**
     * Atualiza um evento existente.
     *
     * @param idEvento       O nome do evento a ser atualizado.
     * @param eventoRequestDTO Objeto contendo os novos detalhes do evento.
     * @return Um objeto de resposta contendo os detalhes do evento atualizado.
     */
    public EventoResponseDTO atualizarEvento(Integer idEvento, EventoRequestDTO eventoRequestDTO) {

        Evento eventoExistente = IEventoJdbcTemplateDAO.procurarPorId(idEvento)
                .orElseThrow(() -> new CustomException(ExceptionMessages.EVENTO_NAO_ENCONTRADO));


//        Evento eventoExistente = IEventoJdbcTemplateDAO.procurarPorNome(nomeEvento)
//                .orElseThrow(() -> new CustomException(ExceptionMessages.EVENTO_NAO_ENCONTRADO));

        Categoria categoria = ICategoriaJdbcTemplateDAO.buscarNomeCategoria(eventoRequestDTO.getCategoria().name());

        if (categoria == null) {
            throw new CustomException(ExceptionMessages.CATEGORIA_INVALIDA);
        }

        if (!validacoes.validarCep(eventoRequestDTO.getCep())) {
            throw new CustomException(ExceptionMessages.CEP_INVALIDO);
        }

        CepResponseDTO cepResponseDTO = cepService.consultarCep(eventoRequestDTO.getCep());
        eventoRequestDTO.setRua(cepResponseDTO.getLogradouro());
        eventoRequestDTO.setBairro(cepResponseDTO.getBairro());
        eventoRequestDTO.setCidade(cepResponseDTO.getLocalidade());
        eventoRequestDTO.setUf(cepResponseDTO.getUf());

        eventoExistente.setNome_evento(eventoRequestDTO.getNome_evento());
        eventoExistente.setDataHora_evento(eventoRequestDTO.getDataHora_evento());
        eventoExistente.setDataHora_eventofinal(eventoRequestDTO.getDataHora_eventofinal());
        eventoExistente.setDescricao(eventoRequestDTO.getDescricao());
        eventoExistente.setId_categoria(categoria.getIdCategoria());

        Evento eventoAtualizado = IEventoJdbcTemplateDAO.atualizarEvento(eventoExistente);

        Endereco enderecoExistente = IEnderecoJdbcTemplateDAO.procurarPorIdEvento(eventoExistente.getIdEvento())
                .orElseThrow(() -> new CustomException(ExceptionMessages.ENDERECO_NAO_ENCONTRADO + idEvento));

        enderecoExistente.setRua(eventoRequestDTO.getRua());
        enderecoExistente.setNumero(eventoRequestDTO.getNumero());
        enderecoExistente.setBairro(eventoRequestDTO.getBairro());
        enderecoExistente.setCidade(eventoRequestDTO.getCidade());
        enderecoExistente.setCep(eventoRequestDTO.getCep());
        enderecoExistente.setUf(eventoRequestDTO.getUf());

        Endereco enderecoAtualizado = IEnderecoJdbcTemplateDAO.atualizarEndereco(enderecoExistente);

        return mapearEvento(categoria, eventoAtualizado, enderecoAtualizado);
    }

    /**
     * Exclui um evento.
     *
     * @param idEvento O ID do evento a ser excluído.
     */
    public void excluirEvento(Integer idEvento) {

        Evento eventoExistente = IEventoJdbcTemplateDAO.procurarPorId(idEvento)
                .orElseThrow(() -> new CustomException(ExceptionMessages.EVENTO_NAO_ENCONTRADO));
        IEnderecoJdbcTemplateDAO.deletarPorIdEvento(idEvento);
        IEventoJdbcTemplateDAO.deletarPorId(idEvento);
    }
}
