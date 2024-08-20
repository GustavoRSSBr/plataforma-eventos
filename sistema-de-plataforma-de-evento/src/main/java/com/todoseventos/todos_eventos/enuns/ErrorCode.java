package com.todoseventos.todos_eventos.enuns;

import lombok.Getter;

@Getter
public enum ErrorCode {
    EMAIL_JA_CADASTRADO ("\"unique_email\"", "Email já cadastrado"),
    ID_PESSOA_CARTEIRA_INVALIDO("O id_pessoa não existe na tabela CARTEIRA", "id informado não existe"),
    EVENTO_NAO_ENCONTRADO("Evento não encontrado", "O evento informado não existe"),
    EVENTO_CANCELADO("Evento está cancelado, ingresso não pode ser comprado", "Evento está cancelado, ingresso não pode ser comprado"),
    INGRESSO_ESGOTADO("Limite de ingressos atingido", "Ingressos esgotados"),
    CARTEIRA_NAO_ENCONTRADA("Carteira não encontrada", "Carteira não encontrada"),
    TIPO_INGRESSO_INVALIDO("Tipo de ingresso inválido", "Tipos disponiveis: inteira, meia e vip"),
    SALDO_INSUFICIENTE("Saldo insuficiente" , "Saldo insuficiente"),
    NENHUM_DADO_ENCONTRADO("Incorrect result size: expected 1, actual 0", "Nenhum dado foi encontrado"),
    EVENTO_JA_CANCELADO("Evento já está cancelado", "Evento já está cancelado"),
    OUTRO_ERRO("Outro erro", "Erro desconhecido");

    private final String message;
    private final String customMessage;

    ErrorCode(String message, String customMessage) {
        this.message = message;
        this.customMessage = customMessage;
    }

    public static ErrorCode fromMessage(String message) {
        for (ErrorCode code : ErrorCode.values()) {
            if (message.contains(code.getMessage())) {
                return code;
            }
        }
        return OUTRO_ERRO;
    }
}
