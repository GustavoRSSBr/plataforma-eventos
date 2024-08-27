package com.todoseventos.todos_eventos.usecase;

import com.todoseventos.todos_eventos.dao.IEmailJdbcTemplateDAO;
import com.todoseventos.todos_eventos.dao.IEnderecoJdbcTemplateDAO;
import com.todoseventos.todos_eventos.dao.IEventoJdbcTemplateDAO;
import com.todoseventos.todos_eventos.dao.INegociacaoJdbcTemplateDAO;
import com.todoseventos.todos_eventos.dto.requestDTO.NegociacaoRequestDTO;
import com.todoseventos.todos_eventos.dto.responseDTO.NegociacaoResponseDTO;
import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.enuns.SuccessMessages;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.model.evento.Email;
import com.todoseventos.todos_eventos.model.evento.Endereco;
import com.todoseventos.todos_eventos.model.evento.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NegociacaoService {

    @Autowired
    private INegociacaoJdbcTemplateDAO iNegociacaoJdbcTemplateDAO;

    @Autowired
    private IEmailJdbcTemplateDAO iEmailJdbcTemplateDAO;

    @Autowired
    private EmailService emailService;

    @Autowired
    private IEventoJdbcTemplateDAO iEventoJdbcTemplateDAO;

    @Autowired
    private IEnderecoJdbcTemplateDAO iEnderecoJdbcTemplateDAO;

    /**
     * Mpaeia o id do evento procurando o id da pessoa e efetua a compra de um ingrsso.
     * @return retorna o resultado da negociação.
     */
    public NegociacaoResponseDTO comprarIngresso(NegociacaoRequestDTO request) {
        iNegociacaoJdbcTemplateDAO.comprarIngresso(request);

        Evento evento = iEventoJdbcTemplateDAO.procurarPorId(request.getIdEvento())
                .orElseThrow(() -> new CustomException(ExceptionMessages.EVENTO_NAO_ENCONTRADO));

        Endereco endereco = iEnderecoJdbcTemplateDAO.procurarPorIdEvento(evento.getIdEvento())
                .orElseThrow(() -> new CustomException(ExceptionMessages.ENDERECO_NAO_ENCONTRADO + evento.getNome_evento()));

        List<Email> envioEmail = iEmailJdbcTemplateDAO.localizarPorIdEvento(request.getIdEvento());
        envioEmail.forEach(pessoa -> {
            String email = pessoa.getEmail();
            String nomePessoa = pessoa.getNome();
            String localEvento = endereco.getRua() + ", " + endereco.getNumero() + ", " + endereco.getBairro() + ", " + endereco.getCidade() + ", " + endereco.getUf();

            emailService.enviarEmail(email, nomePessoa, evento.getNome_evento(), evento.getDataHora_evento(), localEvento);

        });
            return new NegociacaoResponseDTO(SuccessMessages.COMPRA_REALIZADA);
    }
}
