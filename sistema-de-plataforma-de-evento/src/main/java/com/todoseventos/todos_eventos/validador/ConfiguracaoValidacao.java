package com.todoseventos.todos_eventos.validador;

import com.todoseventos.todos_eventos.dto.requestDTO.ClienteAtualizarRequestDTO;
import com.todoseventos.todos_eventos.dto.requestDTO.ClienteRequestDTO;
import com.todoseventos.todos_eventos.dto.requestDTO.DepositoRequestDTO;
import com.todoseventos.todos_eventos.dto.requestDTO.EventoRequestDTO;
import com.todoseventos.todos_eventos.validador.validadoresObjeto.ValidadorClienteAtualizarRequestDTO;
import com.todoseventos.todos_eventos.validador.validadoresObjeto.ValidadorClienteRequestDto;
import com.todoseventos.todos_eventos.validador.validadoresObjeto.ValidadorDepositoRequestDto;
import com.todoseventos.todos_eventos.validador.validadoresObjeto.ValidadorEventoRequestDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class ConfiguracaoValidacao {

    @Bean
    public RegistroEstrategiasValidacao obterRegistro() {
        RegistroEstrategiasValidacao registro = new RegistroEstrategiasValidacao();
        configurarRegistro(registro);
        return registro;
    }

    private void configurarRegistro(RegistroEstrategiasValidacao registro) {
        // Local para registro de estratégia, onde cada classe deve conter apenas uma estratégia de validação
        registro.registrar(ClienteRequestDTO.class, new ValidadorClienteRequestDto());
        registro.registrar(EventoRequestDTO.class, new ValidadorEventoRequestDto());
        registro.registrar(DepositoRequestDTO.class, new ValidadorDepositoRequestDto());
        registro.registrar(ClienteAtualizarRequestDTO.class, new ValidadorClienteAtualizarRequestDTO());
        //outros registros
    }
}
