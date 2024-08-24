package com.todoseventos.todos_eventos.usecase;

import com.todoseventos.todos_eventos.enuns.EmailEnum;
import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    /**
     * Envia um e-mail de confirmação de inscrição.
     *
     * @param email       O endereço de e-mail do destinatário.
     * @param nomePessoa  O nome do destinatário.
     * @param nomeEvento  O nome do evento.
     * @param dataEvento  A data do evento.
     * @param localEvento O local do evento.
     */
    public void enviarEmail(String email, String nomePessoa, String nomeEvento, String dataEvento, String localEvento) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(email);
            helper.setText(gerarCorpoEmail(nomePessoa, nomeEvento, dataEvento, localEvento), true);

            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new CustomException(ExceptionMessages.ERRO_ENVIAR_EMAIL);
        }
    }

    /**
     * Gera o corpo do e-mail de confirmação de inscrição.
     *
     * @param nomePessoa  O nome do destinatário.
     * @param nomeEvento  O nome do evento.
     * @param dataEvento  A data do evento.
     * @param localEvento O local do evento.
     * @return O corpo do e-mail em formato HTML.
     */
    private String gerarCorpoEmail(String nomePessoa, String nomeEvento, String dataEvento, String localEvento) {
        String modelo = EmailEnum.INSCRICAO_GARANTIDA.getModelo();
        return modelo
                .replace("{nomePessoa}", nomePessoa)
                .replace("{nomeEvento}", nomeEvento)
                .replace("{dataEvento}", dataEvento)
                .replace("{localEvento}", localEvento);
    }

    /**
     * Envia um e-mail de cancelamento de evento.
     *
     * @param email      O endereço de e-mail do destinatário.
     * @param nomePessoa O nome do destinatário.
     * @param nomeEvento O nome do evento.
     */
    public void enviarEmailCancelamento(String email, String nomePessoa, String nomeEvento) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(email);
            helper.setSubject("Evento Cancelado: " + nomeEvento);
            helper.setText(gerarCorpoEmailCancelamento(nomePessoa, nomeEvento), true);

            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new CustomException(ExceptionMessages.ERRO_ENVIAR_EMAIL_CANCELAMENTO);
        }
    }

    /**
     * Gera o corpo do e-mail de cancelamento de evento.
     *
     * @param nomePessoa O nome do destinatário.
     * @param nomeEvento O nome do evento.
     * @return O corpo do e-mail em formato HTML.
     */
    private String gerarCorpoEmailCancelamento(String nomePessoa, String nomeEvento) {
        String template = EmailEnum.EVENTO_CANCELADO.getModelo();
        return template
                .replace("{nomePessoa}", nomePessoa)
                .replace("{nomeEvento}", nomeEvento);
    }
}