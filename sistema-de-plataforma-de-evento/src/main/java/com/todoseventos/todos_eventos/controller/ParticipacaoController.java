package com.todoseventos.todos_eventos.controller;

import com.todoseventos.todos_eventos.dto.responseDTO.CustomExceptionResponse;
import com.todoseventos.todos_eventos.dto.requestDTO.ParticipacaoRequest;
import com.todoseventos.todos_eventos.dto.responseDTO.ParticipacaoResponse;
import com.todoseventos.todos_eventos.enuns.SuccessMessages;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.usecase.ParticipacaoService;
import com.todoseventos.todos_eventos.utils.LoggerUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ParticipacaoController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ParticipacaoController.class);

    @Autowired
    private ParticipacaoService participacaoService;

    @Operation(description = "Operação para cadastrar participante no evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscrição realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar o participante!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao realizar cadastro!")
    })
    @PostMapping("/participacao")
    public ResponseEntity<?> inscreverParticipante(
            @Parameter(description = "Dados para inscreve um participante.")
            @Valid @RequestBody ParticipacaoRequest request) {
        long startTime = System.currentTimeMillis();
        ParticipacaoResponse response = participacaoService.inscreverParticipante(request);

        LoggerUtils.logElapsedTime(LOGGER, "inscreverParticipante", startTime);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomExceptionResponse(SuccessMessages.INSCRICAO));
    }

    @Operation(description = "Operação para confirmar participação no evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Participação no evento confirmada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao confirmar participação!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao confirmar participação!")
    })
    @GetMapping("/confirmacao/{idParticipacao}")
    public ResponseEntity<?> confirmarParticipacao(
            @Parameter(description = "Id do participante a ser confirmado.")
            @Valid @PathVariable Integer idParticipacao) {
        long startTime = System.currentTimeMillis();
        ParticipacaoResponse response = participacaoService.confirmarParticipacao(idParticipacao);
        LoggerUtils.logElapsedTime(LOGGER, "confirmarParticipacao", startTime);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
