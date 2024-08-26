package com.todoseventos.todos_eventos.validador.validadoresAtributo;

import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;

import java.util.regex.Pattern;

public class ValidadorSenha {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&*!?+_/*-=]).{8,}$"
    );

    public static void validarSenha(String senha) {
        if (senha == null || !PASSWORD_PATTERN.matcher(senha).matches()) {
            throw new CustomException(ExceptionMessages.SENHA_INVALIDA);
        }
    }
}
