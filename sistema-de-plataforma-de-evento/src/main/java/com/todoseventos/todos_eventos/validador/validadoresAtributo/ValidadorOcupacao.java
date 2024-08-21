package com.todoseventos.todos_eventos.validador.validadoresAtributo;

import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;

public class ValidadorOcupacao {

    public static void validarOcupacao(Integer limite){
        if(limite == null || limite <= 0 || limite > 200000){
            throw new CustomException(ExceptionMessages.LIMITE_INVALIDO);
        }
    }
}
