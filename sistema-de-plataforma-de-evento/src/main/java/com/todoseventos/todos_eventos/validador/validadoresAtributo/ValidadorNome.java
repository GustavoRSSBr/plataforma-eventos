package com.todoseventos.todos_eventos.validador.validadoresAtributo;

import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;

public class ValidadorNome {

    public static void validarNome(String nome){
        if(nome == null || nome.trim().isEmpty() || nome.length() < 10){
            throw new CustomException(ExceptionMessages.NOME_INVALIDO);
        }
    }
}
