package com.todoseventos.todos_eventos.usecase;

import com.todoseventos.todos_eventos.dao.*;
import com.todoseventos.todos_eventos.enuns.CategoriaEnum;
import com.todoseventos.todos_eventos.dto.responseDTO.CepResponse;
import com.todoseventos.todos_eventos.dto.requestDTO.EventoRequest;
import com.todoseventos.todos_eventos.dto.responseDTO.EventoResponse;
import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.gateway.CepService;
import com.todoseventos.todos_eventos.gateway.EmailService;
import com.todoseventos.todos_eventos.model.evento.Categoria;
import com.todoseventos.todos_eventos.model.evento.Endereco;
import com.todoseventos.todos_eventos.model.evento.Evento;
import com.todoseventos.todos_eventos.model.evento.Participacao;
import com.todoseventos.todos_eventos.model.cliente.ClienteFisico;
import com.todoseventos.todos_eventos.model.cliente.ClienteJuridico;
import com.todoseventos.todos_eventos.utils.Validacoes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EventoService {


    @Autowired
    private IEventoDao IEventoDao;

    @Autowired
    private Validacoes validacoes;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CepService cepService;

    @Autowired
    private IEnderecoDao IEnderecoDao;

    @Autowired
    private IParticipacaoDao IParticipacaoDao;

    @Autowired
    private IClienteFisicaDao IClienteFisicaDao;

    @Autowired
    private IClienteJuridicaDao IClienteJuridicaDao;

    @Autowired
    private ICategoriaDao ICategoriaDao;

    /**
     * Cadastra um novo evento.
     * @param eventoRequest Objeto contendo os detalhes do evento a ser cadastrado.
     * @return Um objeto de resposta contendo os detalhes do evento cadastrado.
     */
    public EventoResponse cadastrarNovoEvento(EventoRequest eventoRequest) {

        if (eventoRequest.getCategoria() == null) {
            throw new CustomException(ExceptionMessages.TIPO_CATEGORIA_INVALIDO);
        }
        // Verificar se a categoria é válida
        Categoria categoria = ICategoriaDao.buscarNomeCategoria(eventoRequest.getCategoria().name());

        if (Objects.isNull(categoria)) {
            throw new CustomException(ExceptionMessages.CATEGORIA_INVALIDA);
        }

        // Validar o CEP
        if (!validacoes.validarCep(eventoRequest.getCep())) {
            throw new CustomException(ExceptionMessages.CEP_INVALIDO);
        }

        // Consultar e preencher dados do CEP
        CepResponse cepResponse = cepService.consultarCep(eventoRequest.getCep());
        eventoRequest.setRua(cepResponse.getLogradouro());
        eventoRequest.setBairro(cepResponse.getBairro());
        eventoRequest.setCidade(cepResponse.getLocalidade());
        eventoRequest.setUf(cepResponse.getUf());

        // Criar e salvar o evento
        Evento evento = Evento.builder()
                .nome_evento(eventoRequest.getNome_evento())
                .dataHora_evento(eventoRequest.getDataHora_evento())
                .dataHora_eventofinal(eventoRequest.getDataHora_eventofinal())
                .descricao(eventoRequest.getDescricao())
                .status("ATIVO")
                .id_categoria(categoria.getIdCategoria())
                .build();

        Evento eventoSalvo = IEventoDao.salvarEvento(evento);

        // Criar e salvar o endereço
        Endereco endereco = Endereco.builder()
                .idEvento(eventoSalvo.getIdEvento())
                .rua(eventoRequest.getRua())
                .numero(eventoRequest.getNumero())
                .bairro(eventoRequest.getBairro())
                .cidade(eventoRequest.getCidade())
                .cep(eventoRequest.getCep())
                .uf(eventoRequest.getUf())
                .build();

        Endereco enderecoSalvo = IEnderecoDao.salverEndereco(endereco);

        return mapearEvento(categoria, eventoSalvo, enderecoSalvo);
    }

    /**
     * Encerra um evento.
     * @param idEvento O ID do evento a ser encerrado.
     * @return Um objeto de resposta contendo os detalhes do evento encerrado.
     */
    public EventoResponse encerrarEvento(Integer idEvento) {

        Evento evento = IEventoDao.procurarPorId(idEvento)
                .orElseThrow(() -> new CustomException(ExceptionMessages.EVENTO_NAO_ENCONTRADO));
        evento.setStatus("CANCELADO");
        Evento updatedEvento = IEventoDao.atualizarEvento(evento);

        // Envia e-mails de cancelamento para todos os participantes do evento
        List<Participacao> participacoes = IParticipacaoDao.localizarPorIdEvento(idEvento);
        participacoes.forEach(participacao -> {
            String email;
            String nomePessoa;
            if (participacao.getCpf() != null) {
                ClienteFisico clienteFisica = IClienteFisicaDao.procurarCpf(participacao.getCpf());
                email = clienteFisica.getEmail();
                nomePessoa = clienteFisica.getNome();
            } else {
                ClienteJuridico clienteJuridica = IClienteJuridicaDao.procurarCnpj(participacao.getCnpj());
                email = clienteJuridica.getEmail();
                nomePessoa = clienteJuridica.getNome();
            }
            emailService.enviarEmailCancelamento(email, nomePessoa, evento.getNome_evento());
        });

        return mapearEncerramentoEvento(evento);
    }

    /**
     * Mapeia os detalhes do encerramento de um evento para um objeto de resposta.
     * @param evento O objeto evento contendo os detalhes do evento.
     * @return Um objeto de resposta contendo os detalhes do evento encerrado.
     */
    private EventoResponse mapearEncerramentoEvento(Evento evento) {

        Categoria categoria = ICategoriaDao.procurarId(evento.getId_categoria());
        Endereco endereco = IEnderecoDao.procurarPorIdEvento(evento.getIdEvento())
                .orElseThrow(() -> new CustomException(ExceptionMessages.ENDERECO_NAO_ENCONTRADO + evento.getNome_evento()));

        return EventoResponse.builder()
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
     * @param categoria O objeto categoria contendo os detalhes da categoria do evento.
     * @param eventoSalvo O objeto evento contendo os detalhes do evento salvo.
     * @param enderecoSalvo O objeto endereço contendo os detalhes do endereço salvo.
     * @return Um objeto de resposta contendo os detalhes do evento.
     */
    private EventoResponse mapearEvento(Categoria categoria, Evento eventoSalvo, Endereco enderecoSalvo) {
        return EventoResponse.builder()
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
                .build();
    }

    /**
     * Localiza todos os eventos cadastrados.
     * @return Uma lista de objetos de resposta contendo os detalhes dos eventos localizados.
     */
    public List<EventoResponse> localizarEventos() {

        List<Evento> eventoList;
        List<EventoResponse> eventoResponseList = new ArrayList<>();

        try {
            eventoList = IEventoDao.localizarEvento();
        } catch (Exception e) {
            throw new CustomException(ExceptionMessages.ERRO_BUSCAR_EVENTOS + e.getMessage());
        }

        for (Evento evento : eventoList) {
            Categoria categoria;
            Endereco endereco;

            try {
                categoria = ICategoriaDao.procurarId(evento.getId_categoria());
            } catch (Exception e) {
                throw new CustomException(ExceptionMessages.ERRO_BUSCAR_CATEGORIA_EVENTO + e.getMessage());
            }

            try {
                endereco = IEnderecoDao.procurarPorIdEvento(evento.getIdEvento())
                        .orElseThrow(() -> new CustomException(ExceptionMessages.ENDERECO_NAO_ENCONTRADO + evento.getNome_evento()));
            } catch (Exception e) {
                throw new CustomException(ExceptionMessages.ERRO_BUSCAR_ENDERECO_EVENTO + e.getMessage());
            }

            EventoResponse eventoResponse = mapearEvento(categoria, evento, endereco);
            eventoResponseList.add(eventoResponse);
        }
        return eventoResponseList;
    }

    /**
     * Procura um evento pelo nome.
     * @param nomeEvento O nome do evento a ser procurado.
     * @return Um objeto de resposta contendo os detalhes do evento localizado.
     */
    public EventoResponse procurarEventoPorNome(String nomeEvento) {

        Evento evento = IEventoDao.procurarPorNome(nomeEvento)
                .orElseThrow(() -> new CustomException(ExceptionMessages.EVENTO_NAO_ENCONTRADO));

        Categoria categoria = ICategoriaDao.procurarId(evento.getId_categoria());
        Endereco endereco = IEnderecoDao.procurarPorIdEvento(evento.getIdEvento())
                .orElseThrow(() -> new CustomException(ExceptionMessages.ENDERECO_NAO_ENCONTRADO + nomeEvento));

        return mapearEvento(categoria, evento, endereco);
    }

    /**
     * Atualiza um evento existente.
     * @param nomeEvento O nome do evento a ser atualizado.
     * @param eventoRequest Objeto contendo os novos detalhes do evento.
     * @return Um objeto de resposta contendo os detalhes do evento atualizado.
     */
    public EventoResponse atualizarEvento(String nomeEvento, EventoRequest eventoRequest) {

        Evento eventoExistente = IEventoDao.procurarPorNome(nomeEvento)
                .orElseThrow(() -> new CustomException(ExceptionMessages.EVENTO_NAO_ENCONTRADO));

        Categoria categoria = ICategoriaDao.buscarNomeCategoria(eventoRequest.getCategoria().name());

        if (categoria == null) {
            throw new CustomException(ExceptionMessages.CATEGORIA_INVALIDA);
        }

        // Validar o CEP
        if (!validacoes.validarCep(eventoRequest.getCep())) {
            throw new CustomException(ExceptionMessages.CEP_INVALIDO);
        }

        // Consultar e preencher dados do CEP
        CepResponse cepResponse = cepService.consultarCep(eventoRequest.getCep());
        eventoRequest.setRua(cepResponse.getLogradouro());
        eventoRequest.setBairro(cepResponse.getBairro());
        eventoRequest.setCidade(cepResponse.getLocalidade());
        eventoRequest.setUf(cepResponse.getUf());

        eventoExistente.setNome_evento(eventoRequest.getNome_evento());
        eventoExistente.setDataHora_evento(eventoRequest.getDataHora_evento());
        eventoExistente.setDataHora_eventofinal(eventoRequest.getDataHora_eventofinal());
        eventoExistente.setDescricao(eventoRequest.getDescricao());
        eventoExistente.setId_categoria(categoria.getIdCategoria());

        Evento eventoAtualizado = IEventoDao.atualizarEvento(eventoExistente);

        Endereco enderecoExistente = IEnderecoDao.procurarPorIdEvento(eventoExistente.getIdEvento())
                .orElseThrow(() -> new CustomException(ExceptionMessages.ENDERECO_NAO_ENCONTRADO + nomeEvento));

        enderecoExistente.setRua(eventoRequest.getRua());
        enderecoExistente.setNumero(eventoRequest.getNumero());
        enderecoExistente.setBairro(eventoRequest.getBairro());
        enderecoExistente.setCidade(eventoRequest.getCidade());
        enderecoExistente.setCep(eventoRequest.getCep());
        enderecoExistente.setUf(eventoRequest.getUf());

        Endereco enderecoAtualizado = IEnderecoDao.atualizarEndereco(enderecoExistente);

        return mapearEvento(categoria, eventoAtualizado, enderecoAtualizado);
    }

    /**
     * Exclui um evento.
     * @param idEvento O ID do evento a ser excluído.
     */
    public void excluirEvento(Integer idEvento) {

        Evento eventoExistente = IEventoDao.procurarPorId(idEvento)
                .orElseThrow(() -> new CustomException(ExceptionMessages.EVENTO_NAO_ENCONTRADO));
        IEnderecoDao.deletarPorIdEvento(idEvento);
        IEventoDao.deletarPorId(idEvento);
    }
}
