package com.todoseventos.todos_eventos.validador.validadoresObjeto;

import com.todoseventos.todos_eventos.dto.requestDTO.EventoRequestDTO;

public class ValidadorEventoRequestDto implements IEstrategiaValidacao<EventoRequestDTO>{
    @Override
    public boolean validar(EventoRequestDTO objeto) {
        return false;
    }
}
