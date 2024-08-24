package com.todoseventos.todos_eventos.controller;

import com.todoseventos.todos_eventos.dto.requestDTO.NegociacaoRequestDTO;
import com.todoseventos.todos_eventos.dto.responseDTO.NegociacaoResponseDTO;
import com.todoseventos.todos_eventos.usecase.NegociacaoService;
import com.todoseventos.todos_eventos.utils.LoggerUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador responsável pelas operações de negociação de ingressos.
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class NegociaçãoController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CarteiraController.class);

    @Autowired
    NegociacaoService negociacaoService;

    @Operation(description = "Operação para comprar um ingresso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Compra realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao comprar um evento!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao comprar um ingresso!")
    })
    @PostMapping("/comprar-ingresso")
    public ResponseEntity<?> comprarIngresso(
            @Parameter(description = "Dados necessários para comprar um ingresso.")
            @RequestBody NegociacaoRequestDTO requestDTO) {
        long startTime = System.currentTimeMillis();
        NegociacaoResponseDTO response = negociacaoService.comprarIngresso(requestDTO.getIdEvento(), requestDTO.getIdPessoa(), requestDTO.getTipoIngresso());
        LoggerUtils.logElapsedTime(LOGGER, "comprarIngresso", startTime);
        return ResponseEntity.ok(response);
    }
}
