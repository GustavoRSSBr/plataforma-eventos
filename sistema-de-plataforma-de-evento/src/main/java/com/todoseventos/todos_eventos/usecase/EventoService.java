package com.todoseventos.todos_eventos.usecase;

import com.todoseventos.todos_eventos.dao.ICategoriaJdbcTemplateDAO;
import com.todoseventos.todos_eventos.dao.IEmailJdbcTemplateDAO;
import com.todoseventos.todos_eventos.dao.IEnderecoJdbcTemplateDAO;
import com.todoseventos.todos_eventos.dao.IEventoJdbcTemplateDAO;
import com.todoseventos.todos_eventos.dto.requestDTO.EventoRequestDTO;
import com.todoseventos.todos_eventos.dto.responseDTO.CepResponseDTO;
import com.todoseventos.todos_eventos.dto.responseDTO.EstatisticaResponseDTO;
import com.todoseventos.todos_eventos.dto.responseDTO.EventoResponseDTO;
import com.todoseventos.todos_eventos.enuns.CategoriaEnum;
import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.enuns.StatusEventoEnum;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.gateway.CepService;
import com.todoseventos.todos_eventos.model.evento.Categoria;
import com.todoseventos.todos_eventos.model.evento.Email;
import com.todoseventos.todos_eventos.model.evento.Endereco;
import com.todoseventos.todos_eventos.model.evento.Evento;
import com.todoseventos.todos_eventos.validador.Validador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventoService {

    @Autowired
    private IEventoJdbcTemplateDAO iEventoJdbcTemplateDAO;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CepService cepService;

    @Autowired
    private IEnderecoJdbcTemplateDAO iEnderecoJdbcTemplateDAO;

    @Autowired
    private ICategoriaJdbcTemplateDAO iCategoriaJdbcTemplateDAO;

    @Autowired
    private Validador validador;

    @Autowired
    private IEmailJdbcTemplateDAO iEmailJdbcTemplateDAO;

    /**
     * Cadastra um novo evento.
     *
     * @param eventoRequestDTO Objeto contendo os detalhes do evento a ser cadastrado.
     * @return Um objeto de resposta contendo os detalhes do evento cadastrado.
     */
    public EventoResponseDTO cadastrarNovoEvento(EventoRequestDTO eventoRequestDTO) {

        validador.validar(eventoRequestDTO);

        Categoria categoria = iCategoriaJdbcTemplateDAO.buscarNomeCategoria(eventoRequestDTO.getCategoria().name());

        CepResponseDTO cepResponseDTO = cepService.consultarCep(eventoRequestDTO.getCep());

        if (cepResponseDTO.getLogradouro() == null || cepResponseDTO.getBairro() == null || cepResponseDTO.getLocalidade() == null || cepResponseDTO.getUf() == null) {
            throw new CustomException(ExceptionMessages.CEP_INEXISTENTE);
        }

        eventoRequestDTO.setRua(cepResponseDTO.getLogradouro());
        eventoRequestDTO.setBairro(cepResponseDTO.getBairro());
        eventoRequestDTO.setCidade(cepResponseDTO.getLocalidade());
        eventoRequestDTO.setUf(cepResponseDTO.getUf());

        Evento evento = Evento.builder()
                .nome_evento(eventoRequestDTO.getNome_evento())
                .dataHora_evento(eventoRequestDTO.getDataHora_evento())
                .dataHora_eventofinal(eventoRequestDTO.getDataHora_eventofinal())
                .descricao(eventoRequestDTO.getDescricao())
                .status(StatusEventoEnum.ATIVO)
                .id_categoria(categoria.getIdCategoria())
                .valorIngresso(eventoRequestDTO.getValorIngresso())
                .limitePessoas(eventoRequestDTO.getLimitePessoas())
                .build();

        Evento eventoSalvo = iEventoJdbcTemplateDAO.salvarEvento(evento);

        Endereco endereco = Endereco.builder()
                .idEvento(eventoSalvo.getIdEvento())
                .rua(eventoRequestDTO.getRua())
                .numero(eventoRequestDTO.getNumero())
                .bairro(eventoRequestDTO.getBairro())
                .cidade(eventoRequestDTO.getCidade())
                .cep(eventoRequestDTO.getCep())
                .uf(eventoRequestDTO.getUf())
                .build();

        Endereco enderecoSalvo = iEnderecoJdbcTemplateDAO.salverEndereco(endereco);

        return mapearEvento(categoria, eventoSalvo, enderecoSalvo);
    }

    /**
     * Encerra um evento.
     * Busca os participantes primeiro manda o email de encerramento em seguida encerra o evento e atualiza.
     *
     * @param idEvento O ID do evento a ser encerrado.
     * @return Um objeto de resposta contendo os detalhes do evento encerrado.
     */
    public EventoResponseDTO encerrarEvento(Integer idEvento) {

        Evento evento = iEventoJdbcTemplateDAO.procurarPorId(idEvento)
                .orElseThrow(() -> new CustomException(ExceptionMessages.EVENTO_NAO_ENCONTRADO));

        List<Email> envioEmail = iEmailJdbcTemplateDAO.localizarPorIdEvento(idEvento);
        envioEmail.forEach(participacao -> {
            String email = participacao.getEmail();
            String nomePessoa = participacao.getNome();
            emailService.enviarEmailCancelamento(email, nomePessoa, evento.getNome_evento());
        });

        evento.setStatus(StatusEventoEnum.CANCELADO);
        iEventoJdbcTemplateDAO.encerrarEvento(idEvento);
        Evento updatedEvento = iEventoJdbcTemplateDAO.atualizarEvento(evento);

        return mapearEncerramentoEvento(updatedEvento);
    }

    /**
     * Mapeia os detalhes do encerramento de um evento para um objeto de resposta.
     *
     * @param evento O objeto evento contendo os detalhes do evento.
     * @return Um objeto de resposta contendo os detalhes do evento encerrado.
     */
    private EventoResponseDTO mapearEncerramentoEvento(Evento evento) {

        Categoria categoria = iCategoriaJdbcTemplateDAO.procurarId(evento.getId_categoria());
        Endereco endereco = iEnderecoJdbcTemplateDAO.procurarPorIdEvento(evento.getIdEvento())
                .orElseThrow(() -> new CustomException(ExceptionMessages.ENDERECO_NAO_ENCONTRADO + evento.getNome_evento()));

        return EventoResponseDTO.builder()
                .idEvento(evento.getIdEvento())
                .nome_evento(evento.getNome_evento())
                .dataHora_evento(evento.getDataHora_evento())
                .dataHora_eventofinal(evento.getDataHora_eventofinal())
                .descricao(evento.getDescricao())
                .status(String.valueOf(evento.getStatus()))
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
                .status(String.valueOf(eventoSalvo.getStatus()))
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
            eventoList = iEventoJdbcTemplateDAO.listarEvento();
        } catch (Exception e) {
            throw new CustomException(ExceptionMessages.ERRO_BUSCAR_EVENTOS + e.getMessage());
        }

        for (Evento evento : eventoList) {
            Categoria categoria;
            Endereco endereco;

            try {
                categoria = iCategoriaJdbcTemplateDAO.procurarId(evento.getId_categoria());
            } catch (Exception e) {
                throw new CustomException(ExceptionMessages.ERRO_BUSCAR_CATEGORIA_EVENTO + e.getMessage());
            }

            try {
                endereco = iEnderecoJdbcTemplateDAO.procurarPorIdEvento(evento.getIdEvento())
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

        Evento evento = iEventoJdbcTemplateDAO.procurarPorNome(nomeEvento)
                .orElseThrow(() -> new CustomException(ExceptionMessages.EVENTO_NAO_ENCONTRADO));

        Categoria categoria = iCategoriaJdbcTemplateDAO.procurarId(evento.getId_categoria());
        Endereco endereco = iEnderecoJdbcTemplateDAO.procurarPorIdEvento(evento.getIdEvento())
                .orElseThrow(() -> new CustomException(ExceptionMessages.ENDERECO_NAO_ENCONTRADO + nomeEvento));

        return mapearEvento(categoria, evento, endereco);
    }

    /**
     * Atualiza um evento existente.
     *
     * @param idEvento         O nome do evento a ser atualizado.
     * @param eventoRequestDTO Objeto contendo os novos detalhes do evento.
     * @return Um objeto de resposta contendo os detalhes do evento atualizado.
     */
    public EventoResponseDTO atualizarEvento(Integer idEvento, EventoRequestDTO eventoRequestDTO) {

        Evento eventoExistente = iEventoJdbcTemplateDAO.procurarPorId(idEvento)
                .orElseThrow(() -> new CustomException(ExceptionMessages.EVENTO_NAO_ENCONTRADO));

        validador.validar(eventoRequestDTO);

        Categoria categoria = iCategoriaJdbcTemplateDAO.buscarNomeCategoria(eventoRequestDTO.getCategoria().name());

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
        eventoExistente.setValorIngresso(eventoRequestDTO.getValorIngresso());
        eventoExistente.setLimitePessoas(eventoRequestDTO.getLimitePessoas());

        Evento eventoAtualizado = iEventoJdbcTemplateDAO.atualizarEvento(eventoExistente);

        Endereco enderecoExistente = iEnderecoJdbcTemplateDAO.procurarPorIdEvento(eventoExistente.getIdEvento())
                .orElseThrow(() -> new CustomException(ExceptionMessages.ENDERECO_NAO_ENCONTRADO + idEvento));

        enderecoExistente.setRua(eventoRequestDTO.getRua());
        enderecoExistente.setNumero(eventoRequestDTO.getNumero());
        enderecoExistente.setBairro(eventoRequestDTO.getBairro());
        enderecoExistente.setCidade(eventoRequestDTO.getCidade());
        enderecoExistente.setCep(eventoRequestDTO.getCep());
        enderecoExistente.setUf(eventoRequestDTO.getUf());

        Endereco enderecoAtualizado = iEnderecoJdbcTemplateDAO.atualizarEndereco(enderecoExistente);

        return mapearEvento(categoria, eventoAtualizado, enderecoAtualizado);
    }

    /**
     * Exclui um evento.
     *
     * @param idEvento O ID do evento a ser excluído.
     */
    public void excluirEvento(Integer idEvento) {
        iEventoJdbcTemplateDAO.procurarPorId(idEvento)
                .orElseThrow(() -> new CustomException(ExceptionMessages.EVENTO_NAO_ENCONTRADO));
        iEnderecoJdbcTemplateDAO.deletarPorIdEvento(idEvento);
        iEventoJdbcTemplateDAO.deletarPorId(idEvento);
    }

    public EstatisticaResponseDTO coletarEstatisca(Integer idEvento) {
        return iEventoJdbcTemplateDAO.coletarEstatistica(idEvento);
    }
}
