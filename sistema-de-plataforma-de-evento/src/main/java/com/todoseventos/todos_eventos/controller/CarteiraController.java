package com.todoseventos.todos_eventos.controller;

import com.todoseventos.todos_eventos.dto.requestDTO.DepositoRequestDTO;
import com.todoseventos.todos_eventos.dto.responseDTO.DepositoResponseDTO;
import com.todoseventos.todos_eventos.usecase.CarteiraService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import com.todoseventos.todos_eventos.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class CarteiraController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CarteiraController.class);

    @Autowired
    private CarteiraService carteiraService;

    @PostMapping("/carteira/depositar")
    public ResponseEntity<?> depositar(@Valid @RequestBody DepositoRequestDTO depositoRequest){

        long startTime = System.currentTimeMillis();

        DepositoResponseDTO response = carteiraService.depositar(depositoRequest);

        LoggerUtils.logElapsedTime(LOGGER, "depositar", startTime);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/carteira/saldo/{idPessoa}")
    public ResponseEntity<Double> consultarSaldo(@PathVariable int idPessoa) {
        Double saldo = carteiraService.consultarSaldo(idPessoa);
        return ResponseEntity.ok(saldo);
    }
}
