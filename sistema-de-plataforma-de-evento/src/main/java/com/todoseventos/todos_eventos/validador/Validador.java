package com.todoseventos.todos_eventos.validador;

import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.validador.validadoresObjeto.IEstrategiaValidacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validador {


    private final RegistroEstrategiasValidacao registro;

    @Autowired
    public Validador(ConfiguracaoValidacao configuracao) {
        this.registro = configuracao.obterRegistro();
    }

    public <T> boolean validar(T objeto) {
        IEstrategiaValidacao<T> estrategia = registro.obterEstrategia(objeto.getClass());
        if (estrategia != null) {
            return estrategia.validar(objeto);
        }
        throw new IllegalArgumentException(ExceptionMessages.ESTRATEGIA_NAO_ENCONTRADA + objeto.getClass().getName());
    }
}
