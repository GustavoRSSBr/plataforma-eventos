package com.todoseventos.todos_eventos.validador.validadoresAtributo;

import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;

public class ValidadorDescricao {

    public static void validarDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty() || descricao.length() < 20) {
            throw new CustomException(ExceptionMessages.DESCRICAO_INVALIDA);
        }
    }
}
