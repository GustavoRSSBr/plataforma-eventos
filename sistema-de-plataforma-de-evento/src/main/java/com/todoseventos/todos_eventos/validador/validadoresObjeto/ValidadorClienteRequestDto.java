package com.todoseventos.todos_eventos.validador.validadoresObjeto;

import com.todoseventos.todos_eventos.dto.requestDTO.ClienteRequestDTO;
import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.enuns.TipoClienteEnum;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.validador.validadoresAtributo.*;


public class ValidadorClienteRequestDto implements IEstrategiaValidacao<ClienteRequestDTO> {

    @Override
    public boolean validar(ClienteRequestDTO objeto) {
        if (objeto.getTipo_pessoa() == null) {
            throw new CustomException(ExceptionMessages.CATEGORIA_INVALIDA);
        }

        ValidadorEmail.validarEmail(objeto.getEmail());
        ValidadorTelefone.validarNumeroTelefone(objeto.getTelefone());
        ValidadorSenha.validarSenha(objeto.getSenha());

        if (objeto.getTipo_pessoa() == TipoClienteEnum.FISICA) {
            ValidadorDataNascimento.validarDataNascimento(objeto.getDataNascimento());
            objeto.setCpf(ValidadorCpf.validarCpf(objeto.getCpf()));
        } else if (objeto.getTipo_pessoa() == TipoClienteEnum.JURIDICA) {
            objeto.setCnpj(ValidadorCnpj.validarCnpj(objeto.getCnpj()));
        }

        return true;
    }

}
