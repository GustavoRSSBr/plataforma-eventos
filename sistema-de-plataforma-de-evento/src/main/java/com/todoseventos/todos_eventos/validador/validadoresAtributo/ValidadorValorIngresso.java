package com.todoseventos.todos_eventos.validador.validadoresAtributo;

import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;

import java.math.BigDecimal;

public class ValidadorValorIngresso {

    public static void validarValorIngresso(BigDecimal valorIngresso) {
        if (valorIngresso == null || valorIngresso.compareTo(BigDecimal.ZERO) < 0 || valorIngresso.compareTo(BigDecimal.valueOf(10000)) > 0) {
            throw new CustomException(ExceptionMessages.VALOR_INVALIDO);
        }
    }
}
