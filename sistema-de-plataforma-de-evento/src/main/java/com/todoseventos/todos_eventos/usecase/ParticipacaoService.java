package com.todoseventos.todos_eventos.usecase;

import com.todoseventos.todos_eventos.dao.*;
import com.todoseventos.todos_eventos.dto.requestDTO.ParticipacaoRequest;
import com.todoseventos.todos_eventos.dto.responseDTO.ParticipacaoResponse;
import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.gateway.EmailService;
import com.todoseventos.todos_eventos.model.evento.EnderecoModel;
import com.todoseventos.todos_eventos.model.evento.EventoModel;
import com.todoseventos.todos_eventos.model.evento.ParticipacaoModel;
import com.todoseventos.todos_eventos.model.cliente.ClienteFisicaModel;
import com.todoseventos.todos_eventos.model.cliente.ClienteJuridicaModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ParticipacaoService {

    private static final Logger logger = LoggerFactory.getLogger(ParticipacaoService.class);

    @Autowired
    private IParticipacaoDao IParticipacaoDao;

    @Autowired
    private IEventoDao IEventoDao;

    @Autowired
    private IEnderecoDao IEnderecoDao;

    @Autowired
    private IClienteFisicaDao IClienteFisicaDao;

    @Autowired
    private IClienteJuridicaDao IClienteJuridicaDao;

    @Autowired
    private EmailService emailService;

    /**
     * Inscreve um participante em um evento.
     * @param request Objeto contendo os detalhes da participação, incluindo CPF ou CNPJ e ID do evento.
     * @return Um objeto de resposta de participação contendo os detalhes da inscrição.
     */
    public ParticipacaoResponse inscreverParticipante(ParticipacaoRequest request) {
        logger.info("Iniciando inscrição do participante: {}", request);

        // Procura o evento pelo ID
        EventoModel evento = IEventoDao.procurarPorId(request.getIdEvento())
                .orElseThrow(() -> new CustomException(ExceptionMessages.EVENTO_NAO_ENCONTRADO));

        // Procura o endereço do evento pelo ID do evento

        EnderecoModel endereco = IEnderecoDao.procurarPorIdEvento(evento.getIdEvento())
                .orElseThrow(() -> new CustomException(ExceptionMessages.ENDERECO_NAO_ENCONTRADO + evento.getNome_evento()));

        // Verifica se é um participante pessoa física
        if (request.getCpf() != null) {
            ClienteFisicaModel pessoaFisica = IClienteFisicaDao.procurarCpf(request.getCpf());
            if (Objects.isNull(pessoaFisica)) {
                throw new CustomException(ExceptionMessages.PESSOA_FISICA_NAO_ENCONTRADA);
            }

            // Cria uma nova participação para pessoa física
            ParticipacaoModel participacao = ParticipacaoModel.builder()
                    .cpf(request.getCpf())
                    .idEvento(request.getIdEvento())
                    .status("PENDENTE")
                    .build();
            ParticipacaoModel savedParticipacao = IParticipacaoDao.salvarParticipacao(participacao);

            // Envia e-mail de confirmação para pessoa física
            String localEvento = endereco.getRua() + ", " + endereco.getNumero() + ", " + endereco.getBairro() + ", " + endereco.getCidade() + ", " + endereco.getUf();
            String linkConfirmacao = "http://seusite.com/api/participacao/confirmacao/" + savedParticipacao.getIdParticipacao();

            emailService.enviarEmail(pessoaFisica.getEmail(), "Inscrição Confirmada", pessoaFisica.getNome(), evento.getNome_evento(), evento.getDataHora_evento(), localEvento, linkConfirmacao);
            return PessoaFisica(savedParticipacao, pessoaFisica, evento, endereco);
        } else if (request.getCnpj() != null) {

            // Verifica se é um participante pessoa jurídica
            ClienteJuridicaModel pessoaJuridica = IClienteJuridicaDao.procurarCnpj(request.getCnpj());
            if (Objects.isNull(pessoaJuridica)) {
                throw new CustomException(ExceptionMessages.PESSOA_JURIDICA_NAO_ENCONTRADA);
            }

            // Cria uma nova participação para pessoa jurídica
            ParticipacaoModel participacao = ParticipacaoModel.builder()
                    .cnpj(request.getCnpj())
                    .idEvento(request.getIdEvento())
                    .status("PENDENTE")
                    .build();
            logger.info("Salvando participação para pessoa jurídica: {}", participacao);
            ParticipacaoModel savedParticipacao = IParticipacaoDao.salvarParticipacao(participacao);
            logger.info("Participação salva: {}", savedParticipacao);

            // Envia e-mail de confirmação para pessoa jurídica
            String localEvento = endereco.getRua() + ", " + endereco.getNumero() + ", " + endereco.getBairro() + ", " + endereco.getCidade() + ", " + endereco.getUf();
            String linkConfirmacao = "http://seusite.com/api/participacao/confirmacao/" + savedParticipacao.getIdParticipacao();

            emailService.enviarEmail(pessoaJuridica.getEmail(), "Inscrição Confirmada", pessoaJuridica.getNome(), evento.getNome_evento(), evento.getDataHora_evento(), localEvento, linkConfirmacao);
            return PessoaJuridica(savedParticipacao, pessoaJuridica, evento, endereco);
        } else {
            // Lança exceção se nem CPF nem CNPJ foram informados
            throw new CustomException(ExceptionMessages.CPF_OU_CNPJ_NAO_INFORMADOS);
        }
    }

    /**
     * Confirma a participação de um participante em um evento.
     * @param idParticipacao O ID da participação a ser confirmada.
     * @return Um objeto de resposta de participação contendo os detalhes da confirmação.
     */
    public ParticipacaoResponse confirmarParticipacao(Integer idParticipacao) {
        logger.info("Confirmando participação com ID: {}", idParticipacao);
        ParticipacaoModel participacao = IParticipacaoDao.localizarPorId(idParticipacao);
        if (Objects.isNull(participacao)) {
            throw new CustomException(ExceptionMessages.PARTICIPACAO_NAO_ENCONTRADA);
        }

        // Atualiza o status da participação para "CONFIRMADO"
        participacao.setStatus("CONFIRMADO");
        ParticipacaoModel updatedParticipacao = IParticipacaoDao.atualizarParticipacao(participacao);
        logger.info("Participação confirmada: {}", updatedParticipacao);

        // Obtém os detalhes do evento e endereço associados
        EventoModel evento = IEventoDao.procurarPorId(updatedParticipacao.getIdEvento())
                .orElseThrow(() -> new CustomException(ExceptionMessages.EVENTO_NAO_ENCONTRADO));
        EnderecoModel endereco = IEnderecoDao.procurarPorIdEvento(evento.getIdEvento())
                .orElseThrow(() -> new CustomException(ExceptionMessages.ENDERECO_NAO_ENCONTRADO + evento.getNome_evento()));

        // Envia e-mail de confirmação de participação
        if (updatedParticipacao.getCpf() != null) {
            ClienteFisicaModel pessoaFisica = IClienteFisicaDao.procurarCpf(updatedParticipacao.getCpf());
            emailService.enviarEmailConfirmacao(pessoaFisica.getEmail(), "Confirmação de Participação", pessoaFisica.getNome(), evento.getNome_evento(), evento.getDataHora_evento(), endereco);
            return PessoaFisica(updatedParticipacao, pessoaFisica, evento, endereco);
        } else {
            ClienteJuridicaModel pessoaJuridica = IClienteJuridicaDao.procurarCnpj(updatedParticipacao.getCnpj());
            emailService.enviarEmailConfirmacao(pessoaJuridica.getEmail(), "Confirmação de Participação", pessoaJuridica.getNome(), evento.getNome_evento(), evento.getDataHora_evento(), endereco);
            return PessoaJuridica(updatedParticipacao, pessoaJuridica, evento, endereco);
        }
    }

    /**
     * Cria um objeto de resposta de participação para pessoa física.
     * @param participacao O objeto de participação contendo os detalhes.
     * @param pessoaFisica O objeto pessoa física contendo os detalhes do participante.
     * @param evento O objeto evento contendo os detalhes do evento.
     * @param endereco O objeto endereço contendo os detalhes do local do evento.
     * @return Um objeto de resposta de participação.
     */
    public static ParticipacaoResponse PessoaFisica(ParticipacaoModel participacao, ClienteFisicaModel pessoaFisica, EventoModel evento, EnderecoModel endereco) {
        return ParticipacaoResponse.builder()
                .idParticipacao(participacao.getIdParticipacao())
                .nomePessoa(pessoaFisica.getNome())
                .emailPessoa(pessoaFisica.getEmail())
                .cpfPessoa(participacao.getCpf())
                .nomeEvento(evento.getNome_evento())
                .dataHoraEvento(evento.getDataHora_evento())
                .dataHoraEventoFinal(evento.getDataHora_eventofinal())
                .status(participacao.getStatus())
                .localEvento(endereco.getRua() + ", " + endereco.getNumero() + ", " + endereco.getBairro() + ", " + endereco.getCidade() + ", " + endereco.getUf())
                .build();
    }

    /**
     * Cria um objeto de resposta de participação para pessoa jurídica.
     * @param participacao O objeto de participação contendo os detalhes.
     * @param pessoaJuridica O objeto pessoa jurídica contendo os detalhes do participante.
     * @param evento O objeto evento contendo os detalhes do evento.
     * @param endereco O objeto endereço contendo os detalhes do local do evento.
     * @return Um objeto de resposta de participação.
     */
    public static ParticipacaoResponse PessoaJuridica(ParticipacaoModel participacao, ClienteJuridicaModel pessoaJuridica, EventoModel evento, EnderecoModel endereco) {
        return ParticipacaoResponse.builder()
                .idParticipacao(participacao.getIdParticipacao())
                .nomePessoa(pessoaJuridica.getNome())
                .emailPessoa(pessoaJuridica.getEmail())
                .cnpjPessoa(participacao.getCnpj())
                .nomeEvento(evento.getNome_evento())
                .dataHoraEvento(evento.getDataHora_evento())
                .dataHoraEventoFinal(evento.getDataHora_eventofinal())
                .status(participacao.getStatus())
                .localEvento(endereco.getRua() + ", " + endereco.getNumero() + ", " + endereco.getBairro() + ", " + endereco.getCidade() + ", " + endereco.getUf())
                .build();
    }
}
