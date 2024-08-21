package com.todoseventos.todos_eventos.validador.validadoresAtributo;

import com.todoseventos.todos_eventos.enuns.CategoriaEnum;
import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;
public class ValidadorCategoria {

    public static void validarCategoria(CategoriaEnum categoria){
        if(categoria == null){
            throw new CustomException(ExceptionMessages.CATEGORIA_INVALIDA);
        }
    }
}
