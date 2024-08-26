package com.todoseventos.todos_eventos.validador.validadoresObjeto;

import com.todoseventos.todos_eventos.dto.requestDTO.ClienteAtualizarRequestDTO;
import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.validador.validadoresAtributo.ValidadorEmail;
import com.todoseventos.todos_eventos.validador.validadoresAtributo.ValidadorNome;
import com.todoseventos.todos_eventos.validador.validadoresAtributo.ValidadorSenha;
import com.todoseventos.todos_eventos.validador.validadoresAtributo.ValidadorTelefone;

public class ValidadorClienteAtualizarRequestDTO implements IEstrategiaValidacao<ClienteAtualizarRequestDTO> {

    @Override
    public boolean validar(ClienteAtualizarRequestDTO objeto) {

        if (objeto.getNome() == null || objeto.getNome().isEmpty()) {
            throw new CustomException(ExceptionMessages.CAMPO_FIXO);
        }

        if (objeto.getEmail() == null || objeto.getEmail().isEmpty()) {
            throw new CustomException(ExceptionMessages.CAMPO_FIXO);
        }

        if (objeto.getSenha() == null || objeto.getSenha().isEmpty()) {
            throw new CustomException(ExceptionMessages.CAMPO_FIXO);
        }

        if (objeto.getTelefone() == null || objeto.getTelefone().isEmpty()) {
            throw new CustomException(ExceptionMessages.CAMPO_FIXO);
        }

        ValidadorNome.validarNome(objeto.getNome());
        ValidadorEmail.validarEmail(objeto.getEmail());
        ValidadorSenha.validarSenha(objeto.getSenha());
        ValidadorTelefone.validarNumeroTelefone(objeto.getTelefone());

        return true;
    }
}
