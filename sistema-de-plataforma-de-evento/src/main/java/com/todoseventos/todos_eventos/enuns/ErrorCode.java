package com.todoseventos.todos_eventos.enuns;

import lombok.Getter;

@Getter
public enum ErrorCode {
    EMAIL_JA_CADASTRADO ("\"unique_email\"", "Email já cadastrado"),
    ID_PESSOA_CARTEIRA_INVALIDO("O id_pessoa não existe na tabela CARTEIRA", "id informado não existe"),
    EVENTO_NAO_ENCONTRADO("Evento não encontrado", "O evento informado não existe"),
    EVENTO_CANCELADO("Evento cancelado", "O evento foi cancelado. Pedimos desculpas pelo inconveniente."),
    INGRESSO_ESGOTADO("Limite de ingressos atingido", "Ingressos esgotados"),
    CARTEIRA_NAO_ENCONTRADA("Carteira não encontrada", "Carteira não encontrada"),
    ID_NAO_ENCONTRADO("Id inválido", "Id do cliente não encontrado."),
    TIPO_INGRESSO_INVALIDO("Tipo de ingresso inválido", "Tipos disponiveis: inteira, meia e vip"),
    SALDO_INSUFICIENTE("Saldo insuficiente" , "Saldo insuficiente"),
    NENHUM_DADO_ENCONTRADO("Incorrect result size: expected 1, actual 0", "Nenhum dado foi encontrado"),
    EVENTO_JA_CANCELADO("Evento já está cancelado", "Evento já está cancelado"),
    CPF_JA_CADASTRADO("unicidade \"pessoa_fisica_pkey\"", "CPF já cadastrado"),
    CNPJ_JA_CADASTRADO("unicidade \"pessoa_juridica_pkey\"", "CNPJ já cadastrado"),
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