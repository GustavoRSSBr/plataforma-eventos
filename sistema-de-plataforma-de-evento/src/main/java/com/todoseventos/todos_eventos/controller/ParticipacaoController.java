package com.todoseventos.todos_eventos.controller;

import com.todoseventos.todos_eventos.dto.responseDTO.CustomExceptionResponseDTO;
import com.todoseventos.todos_eventos.dto.requestDTO.ParticipacaoRequestDTO;
import com.todoseventos.todos_eventos.dto.responseDTO.ParticipacaoResponseDTO;
import com.todoseventos.todos_eventos.enuns.SuccessMessages;
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
            @Valid @RequestBody ParticipacaoRequestDTO request) {
        long startTime = System.currentTimeMillis();
        ParticipacaoResponseDTO response = participacaoService.inscreverParticipante(request);

        LoggerUtils.logElapsedTime(LOGGER, "inscreverParticipante", startTime);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomExceptionResponseDTO(SuccessMessages.INSCRICAO));
    }

}
