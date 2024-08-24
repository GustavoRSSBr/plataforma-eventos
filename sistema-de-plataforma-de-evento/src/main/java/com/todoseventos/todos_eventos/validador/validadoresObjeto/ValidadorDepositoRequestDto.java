package com.todoseventos.todos_eventos.validador.validadoresObjeto;

import com.todoseventos.todos_eventos.dto.requestDTO.DepositoRequestDTO;
import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;

public class ValidadorDepositoRequestDto implements IEstrategiaValidacao<DepositoRequestDTO> {
    @Override
    public boolean validar(DepositoRequestDTO objeto) {

        if (objeto.getIdPessoa() == null) {
            throw new CustomException(ExceptionMessages.ID_PESSOA_NAO_INFORMADO);
        }

        if (objeto.getValor() == null || objeto.getValor() <= 0) {
            throw new CustomException(ExceptionMessages.VALOR_DEPOSITO_INVALIDO);
        }

        return true;
    }
}
