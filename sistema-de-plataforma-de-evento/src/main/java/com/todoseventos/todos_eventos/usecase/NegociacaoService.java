package com.todoseventos.todos_eventos.usecase;

import com.todoseventos.todos_eventos.dao.IEmailJdbcTemplateDAO;
import com.todoseventos.todos_eventos.dao.IEnderecoJdbcTemplateDAO;
import com.todoseventos.todos_eventos.dao.IEventoJdbcTemplateDAO;
import com.todoseventos.todos_eventos.dao.INegociacaoJdbcTemplateDAO;
import com.todoseventos.todos_eventos.dto.responseDTO.NegociacaoResponseDTO;
import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.model.evento.Email;
import com.todoseventos.todos_eventos.model.evento.Endereco;
import com.todoseventos.todos_eventos.model.evento.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NegociacaoService {

    @Autowired
    INegociacaoJdbcTemplateDAO iNegociacaoJdbcTemplateDAO;

    @Autowired
    IEmailJdbcTemplateDAO iEmailJdbcTemplateDAO;

    @Autowired
    EmailService emailService;

    @Autowired
    IEventoJdbcTemplateDAO iEventoJdbcTemplateDAO;

    @Autowired
    IEnderecoJdbcTemplateDAO iEnderecoJdbcTemplateDAO;

    /**
     * Mpaeia o id do evento procurando o id da pessoa e efetua a compra de um ingrsso.
     *
     * @param idEvento para buscar o evento.
     * @param idPessoa para buscar a pessoa.
     * @param tipoIngresso para buscar o tipo do ingresso: inteira, meia ou vip.
     * @return retorna o resultado da negociação.
     */
    public NegociacaoResponseDTO comprarIngresso(Integer idEvento, Integer idPessoa, String tipoIngresso) {
        String resultado = iNegociacaoJdbcTemplateDAO.comprarIngresso(idEvento, idPessoa, tipoIngresso);

        Evento evento = iEventoJdbcTemplateDAO.procurarPorId(idEvento)
                .orElseThrow(() -> new CustomException(ExceptionMessages.EVENTO_NAO_ENCONTRADO));

        Endereco endereco = iEnderecoJdbcTemplateDAO.procurarPorIdEvento(evento.getIdEvento())
                .orElseThrow(() -> new CustomException(ExceptionMessages.ENDERECO_NAO_ENCONTRADO + evento.getNome_evento()));

        List<Email> envioEmail = iEmailJdbcTemplateDAO.localizarPorIdEvento(idEvento);
        envioEmail.forEach(participacao -> {
            String email = participacao.getEmail();
            String nomePessoa = participacao.getNome();
            String localEvento = endereco.getRua() + ", " + endereco.getNumero() + ", " + endereco.getBairro() + ", " + endereco.getCidade() + ", " + endereco.getUf();

            emailService.enviarEmail(email, nomePessoa, evento.getNome_evento(), evento.getDataHora_evento(), localEvento);

        });

            return new NegociacaoResponseDTO(resultado);
    }
}
