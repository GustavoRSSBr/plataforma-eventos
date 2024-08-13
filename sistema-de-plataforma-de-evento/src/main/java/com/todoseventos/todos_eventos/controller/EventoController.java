package com.todoseventos.todos_eventos.controller;

import com.todoseventos.todos_eventos.dto.responseDTO.CustomExceptionResponse;
import com.todoseventos.todos_eventos.dto.requestDTO.EventoRequest;
import com.todoseventos.todos_eventos.dto.responseDTO.EventoResponse;
import com.todoseventos.todos_eventos.enuns.SuccessMessages;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.usecase.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Operation(description = "Operação para cadastrado de evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso. Seu evento já está em divulgação!"),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar o evento!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao realizar cadastro!")
    })
    @PostMapping("/evento")
    public ResponseEntity<CustomExceptionResponse> cadastrarEvento(@RequestBody EventoRequest eventoRequest) {
        EventoResponse response = eventoService.cadastrarNovoEvento(eventoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomExceptionResponse(SuccessMessages.CADASTRO_EVENTO));
    }

    @Operation(description = "Operação para encerrar evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento encerrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao encerrar evento!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao encerrar evento!")
    })
    @PutMapping("/encerrar/{idEvento}")
    public ResponseEntity<?> encerrarEvento(@PathVariable Integer idEvento) {
        EventoResponse response = eventoService.encerrarEvento(idEvento);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomExceptionResponse(SuccessMessages.EVENTO_ENCERRADO));
    }

    @Operation(description = "Operação para listar todos os eventos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eventos recuperada com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao listar eventos!")
    })
    @GetMapping("/evento")
    public ResponseEntity<?> listarEventos() {
        List<EventoResponse> response = eventoService.localizarEventos();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(description = "Operação para buscar evento por nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento encontrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao procurar evento!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao procurar evento!")
    })
    @GetMapping("/evento/{nomeEvento}")
    public ResponseEntity<?> procurarEventoPorNome(@PathVariable String nomeEvento) {
        EventoResponse response = eventoService.procurarEventoPorNome(nomeEvento);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(description = "Operação para atualizar evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar evento!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao atualizar evento!")
    })
    @PutMapping("/evento/{nomeEvento}")
    public ResponseEntity<?> atualizarEvento(@PathVariable String nomeEvento, @RequestBody EventoRequest eventoRequest) {
        EventoResponse response = eventoService.atualizarEvento(nomeEvento, eventoRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomExceptionResponse(SuccessMessages.EVENTO_ATUALIZADO));
    }

    @Operation(description = "Operação para excluir evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento excluído com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao excluir evento!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao excluir evento!")
    })
    @DeleteMapping("/evento/{idEvento}")
    public ResponseEntity<?> excluirEvento(@PathVariable Integer idEvento) {
        eventoService.excluirEvento(idEvento);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomExceptionResponse(SuccessMessages.EXCLUIR_EVENTO));
    }
}
