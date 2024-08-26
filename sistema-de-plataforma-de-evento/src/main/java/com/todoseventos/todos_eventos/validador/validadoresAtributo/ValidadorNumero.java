package com.todoseventos.todos_eventos.validador.validadoresAtributo;

import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;

public class ValidadorNumero {

    public static void validarNumero(String numero) {
        if (numero == null || numero.trim().isEmpty() || !numero.matches("\\d+")) {
            throw new CustomException(ExceptionMessages.NUMERO_INVALIDO);
        }
    }
}
