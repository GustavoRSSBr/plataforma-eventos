package com.todoseventos.todos_eventos.validador.validadoresAtributo;

import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;

public class ValidadorCep {
    public static void validarCep(String cep) {
        if (cep == null || cep.isEmpty()) {
            throw new CustomException(ExceptionMessages.CEP_EM_BRANCO);
        }

        String regex = "^[0-9]{5}-[0-9]{3}$";
        if (!cep.matches(regex)) {
            throw new CustomException(ExceptionMessages.CEP_INVALIDO);
        }
    }

}
