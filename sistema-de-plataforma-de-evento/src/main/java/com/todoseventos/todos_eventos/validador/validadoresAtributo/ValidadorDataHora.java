package com.todoseventos.todos_eventos.validador.validadoresAtributo;

import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidadorDataHora {

    private static final DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void validarDataHora(String inicio, String fim){
        LocalDateTime dataHoraInicio = parseDataHora(inicio, "Data inválida");
        LocalDateTime dataHoraFinal = parseDataHora(fim, "Data inválida");

        if(dataHoraInicio.isBefore(LocalDateTime.now())){
            throw new CustomException(ExceptionMessages.DATA_INICIO_INVALIDA);
        }
        if(dataHoraFinal.isAfter(LocalDateTime.now().plusYears(3))){
            throw new CustomException(ExceptionMessages.DATA_FINAL_INVALIDA);
        }
        if(dataHoraFinal.isBefore(dataHoraInicio)){
            throw new CustomException(ExceptionMessages.DATA_EVENTO_INVALIDA);
        }
    }

    private static LocalDateTime parseDataHora(String dataHora, String errorMessage){
        try{
            return LocalDateTime.parse(dataHora, formato);
        } catch (DateTimeParseException e){
            throw new CustomException(errorMessage);
        }
    }
}
