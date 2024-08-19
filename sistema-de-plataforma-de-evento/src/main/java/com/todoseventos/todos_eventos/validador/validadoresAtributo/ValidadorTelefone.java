package com.todoseventos.todos_eventos.validador.validadoresAtributo;

import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;

import java.util.regex.Pattern;

public class ValidadorTelefone{
    private static final String PHONE_NUMBER_PATTERN = "^[0-9()\\-\\s]+$";
    private static final Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
    public static void validarNumeroTelefone(String numeroTelefone) {
        if (numeroTelefone == null || numeroTelefone.trim().isEmpty() || !pattern.matcher(numeroTelefone).matches()) {
            throw new CustomException(ExceptionMessages.TELEFONE_INVALIDO);
        }
    }
}
