package com.todoseventos.todos_eventos.controller;

import com.todoseventos.todos_eventos.dto.responseDTO.CustomExceptionResponseDTO;
import com.todoseventos.todos_eventos.dto.requestDTO.EventoRequestDTO;
import com.todoseventos.todos_eventos.dto.responseDTO.EventoResponseDTO;
import com.todoseventos.todos_eventos.enuns.SuccessMessages;
import com.todoseventos.todos_eventos.usecase.EventoService;
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

import java.util.List;
@Slf4j
@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class EventoController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(EventoController.class);

    @Autowired
    private EventoService eventoService;

    @Operation(description = "Operação para cadastrado de evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso. Seu evento já está em divulgação!"),
            @ApiResponse(responseCode = "400", description = "Erro ao cadastrar o evento!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao realizar cadastro!")
    })
    @PostMapping("/evento")
    public ResponseEntity<CustomExceptionResponseDTO> cadastrarEvento(
            @Parameter(description = "Dados necessários para cadastrar um novo evento.")
            @Valid @RequestBody EventoRequestDTO eventoRequestDTO) {
        long startTime = System.currentTimeMillis();
        EventoResponseDTO response = eventoService.cadastrarNovoEvento(eventoRequestDTO);

        LoggerUtils.logElapsedTime(LOGGER, "cadastrarEvento", startTime);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomExceptionResponseDTO(SuccessMessages.CADASTRO_EVENTO));
    }

    @Operation(description = "Operação para encerrar evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento encerrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao encerrar evento!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao encerrar evento!")
    })
    @PutMapping("/encerrar/{idEvento}")
    public ResponseEntity<?> encerrarEvento(
            @Parameter(description = "Id do evento a ser encerrado.")
            @Valid @PathVariable Integer idEvento) {
        long startTime = System.currentTimeMillis();
        EventoResponseDTO response = eventoService.encerrarEvento(idEvento);

        LoggerUtils.logElapsedTime(LOGGER, "encerrarEvento", startTime);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomExceptionResponseDTO(SuccessMessages.EVENTO_ENCERRADO));

    }

    @Operation(description = "Operação para listar todos os eventos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eventos recuperada com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao listar eventos!")
    })
    @GetMapping("/evento")
    public ResponseEntity<?> listarEventos() {
        long startTime = System.currentTimeMillis();
        List<EventoResponseDTO> response = eventoService.localizarEventos();
        LoggerUtils.logElapsedTime(LOGGER, "listarEventos", startTime);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(description = "Operação para buscar evento por nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento encontrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao procurar evento!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao procurar evento!")
    })
    @GetMapping("/evento/{nomeEvento}")
    public ResponseEntity<?> procurarEventoPorNome(
            @Parameter(description = "Nome do evento a ser procurado.")
            @Valid @PathVariable String nomeEvento) {
        long startTime = System.currentTimeMillis();
        EventoResponseDTO response = eventoService.procurarEventoPorNome(nomeEvento);
        LoggerUtils.logElapsedTime(LOGGER, "procurarEventoPorNome", startTime);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(description = "Operação para atualizar evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar evento!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao atualizar evento!")
    })
    @PutMapping("/evento/{nomeEvento}")
    public ResponseEntity<?> atualizarEvento(
            @Parameter(description = "Nome do evento a ser atualizado.")
            @Valid @PathVariable String nomeEvento, @RequestBody EventoRequestDTO eventoRequestDTO) {
        long startTime = System.currentTimeMillis();
        EventoResponseDTO response = eventoService.atualizarEvento(nomeEvento, eventoRequestDTO);

        LoggerUtils.logElapsedTime(LOGGER, "atualizarEvento", startTime);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomExceptionResponseDTO(SuccessMessages.EVENTO_ATUALIZADO));
    }

    @Operation(description = "Operação para excluir evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento excluído com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro ao excluir evento!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ao excluir evento!")
    })
    @DeleteMapping("/evento/{idEvento}")
    public ResponseEntity<?> excluirEvento(
            @Parameter(description = "Id do evento a ser excluído.")
            @Valid @PathVariable Integer idEvento) {
        long startTime = System.currentTimeMillis();
        eventoService.excluirEvento(idEvento);

        LoggerUtils.logElapsedTime(LOGGER, "excluirEvento", startTime);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomExceptionResponseDTO(SuccessMessages.EXCLUIR_EVENTO));
    }
}
