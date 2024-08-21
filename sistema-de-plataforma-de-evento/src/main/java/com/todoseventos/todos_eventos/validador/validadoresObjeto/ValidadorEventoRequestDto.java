package com.todoseventos.todos_eventos.validador.validadoresObjeto;

import com.todoseventos.todos_eventos.dto.requestDTO.EventoRequestDTO;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.validador.validadoresAtributo.*;

public class ValidadorEventoRequestDto implements IEstrategiaValidacao<EventoRequestDTO>{
    @Override
    public boolean validar(EventoRequestDTO objeto){
        if(objeto == null){
            throw new CustomException("DADOS NÃO PODEM SER NULOS.");
        }

        ValidadorNome.validarNome(objeto.getNome_evento());
        ValidadorDataHora.validarDataHora(objeto.getDataHora_evento(), objeto.getDataHora_eventofinal());
        ValidadorDescricao.validarDescricao(objeto.getDescricao());
        ValidadorCategoria.validarCategoria(objeto.getCategoria());
        ValidadorValorIngresso.validarValorIngresso(objeto.getValorIngresso());
        ValidadorOcupacao.validarOcupacao(objeto.getLimitePessoas());
        ValidadorNumero.validarNumero(objeto.getNumero());
        ValidadorCep.validarCep(objeto.getCep());

        return true;
    }
}
