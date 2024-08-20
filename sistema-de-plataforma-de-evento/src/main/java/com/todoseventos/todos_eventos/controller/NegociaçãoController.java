package com.todoseventos.todos_eventos.controller;

import com.todoseventos.todos_eventos.dto.requestDTO.NegociacaoRequestDTO;
import com.todoseventos.todos_eventos.dto.responseDTO.NegociacaoResponseDTO;
import com.todoseventos.todos_eventos.model.cliente.Cliente;
import com.todoseventos.todos_eventos.usecase.NegociacaoService;
import com.todoseventos.todos_eventos.utils.LoggerUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1")
public class NegociaçãoController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CarteiraController.class);

    @Autowired
    NegociacaoService negociacaoService;


    @PostMapping("/comprar-ingresso")
    public ResponseEntity<?> comprarIngresso(@RequestBody NegociacaoRequestDTO requestDTO) {
        long startTime = System.currentTimeMillis();
        NegociacaoResponseDTO response = negociacaoService.comprarIngresso(requestDTO.getIdEvento(), requestDTO.getIdPessoa(), requestDTO.getTipoIngresso());
        LoggerUtils.logElapsedTime(LOGGER, "comprarIngresso", startTime);
        return ResponseEntity.ok(response);
    }
}
