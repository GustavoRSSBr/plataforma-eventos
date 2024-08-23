package com.todoseventos.todos_eventos.validador.validadoresAtributo;

import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidadorDataNascimento{

    public static void validarData(String dataNascimento) {

        if(!validarDataNascimento(dataNascimento)){
            throw new CustomException(ExceptionMessages.DATA_NASCIMENTO_INVALIDA);
        }

    }

    public static boolean validarDataNascimento(String dataNascimento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate data = LocalDate.parse(dataNascimento, formatter);
            LocalDate dataAtual = LocalDate.now();

            return !data.isAfter(dataAtual);

        } catch (DateTimeParseException e) {
            return false;
        }
    }

}
