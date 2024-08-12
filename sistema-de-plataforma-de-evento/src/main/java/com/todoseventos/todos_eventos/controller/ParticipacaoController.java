package com.todoseventos.todos_eventos.controller;

import com.todoseventos.todos_eventos.dto.CustomExceptionResponse;
import com.todoseventos.todos_eventos.dto.ParticipacaoRequest;
import com.todoseventos.todos_eventos.dto.ParticipacaoResponse;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.usecase.ParticipacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ParticipacaoController {

    @Autowired
    private ParticipacaoService participacaoService;

    @Operation(description = "Operação para cadastrar participante no evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscrição realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar o participante!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao realizar cadastro!")
    })
    @PostMapping("/participacao")
    public ResponseEntity<?> inscreverParticipante(@RequestBody ParticipacaoRequest request) {
        ParticipacaoResponse response = participacaoService.inscreverParticipante(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomExceptionResponse(CustomException.INSCRICAO));
    }

    @Operation(description = "Operação para confirmar participação no evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Participação no evento confirmada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao confirmar participação!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao confirmar participação!")
    })
    @GetMapping("/confirmacao/{idParticipacao}")
    public ResponseEntity<?> confirmarParticipacao(@PathVariable Integer idParticipacao) {
        ParticipacaoResponse response = participacaoService.confirmarParticipacao(idParticipacao);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
