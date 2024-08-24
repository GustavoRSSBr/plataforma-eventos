package com.todoseventos.todos_eventos.validador.validadoresAtributo;

import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;

public class ValidadorCnpj {
    public static String validarCnpj(String campo) {
        return isCnpjValid(campo);
    }

    public static String isCnpjValid(String cnpj) {
        cnpj = cnpj.replaceAll("[^\\d]", "");

        if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}"))
            throw new CustomException(ExceptionMessages.CNPJ_INVALIDO);

        char dig13, dig14;
        int sm, i, r, num, peso;

        // Cálculo do primeiro dígito verificador
        sm = 0;
        peso = 2;
        for (i = 11; i >= 0; i--) {
            num = (int) (cnpj.charAt(i) - 48);
            sm += (num * peso);
            peso++;
            if (peso == 10) peso = 2;
        }
        r = sm % 11;
        dig13 = (r == 0 || r == 1) ? '0' : (char) ((11 - r) + 48);

        // Cálculo do segundo dígito verificador
        sm = 0;
        peso = 2;
        for (i = 12; i >= 0; i--) {
            num = (int) (cnpj.charAt(i) - 48);
            sm += (num * peso);
            peso++;
            if (peso == 10) peso = 2;
        }
        r = sm % 11;
        dig14 = (r == 0 || r == 1) ? '0' : (char) ((11 - r) + 48);
        if (!(dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13))) {
            throw new CustomException(ExceptionMessages.CNPJ_INVALIDO);
        }

        return cnpj;
    }
}
