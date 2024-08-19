package com.todoseventos.todos_eventos.validador.validadoresAtributo;

import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;

public class ValidadorCpf {

    public static void validarCpf(String campo) {

        if(!isCpfValid(campo)){
            throw new CustomException(ExceptionMessages.CPF_INVALIDO);
        }

    }

    public static boolean isCpfValid(String cpf) {
        cpf = cpf.replaceAll("[^\\d]", ""); // Remove todos os caracteres não numéricos
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}"))
            return false;
        char dig10, dig11;
        int sm, i, r, num, peso;


            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (cpf.charAt(i) - 48);
                sm += (num * peso);
                peso--;
            }
            r = 11 - (sm % 11);
            dig10 = (r == 10 || r == 11) ? '0' : (char) (r + 48);

            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (cpf.charAt(i) - 48);
                sm += (num * peso);
                peso--;
            }
            r = 11 - (sm % 11);
            dig11 = (r == 10 || r == 11) ? '0' : (char) (r + 48);

            return (dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10));

    }


}
