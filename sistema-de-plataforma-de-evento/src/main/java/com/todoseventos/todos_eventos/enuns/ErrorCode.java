package com.todoseventos.todos_eventos.enuns;

import lombok.Getter;

@Getter
public enum ErrorCode {
    EMAIL_JA_CADASTRADO ("\"unique_email\"", "Email já cadastrado"),
    ID_PESSOA_CARTEIRA_INVALIDO("O id_pessoa não existe na tabela CARTEIRA", "id informado não existe"),
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
