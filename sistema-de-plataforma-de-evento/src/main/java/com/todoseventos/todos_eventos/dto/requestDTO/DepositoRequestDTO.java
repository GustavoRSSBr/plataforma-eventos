package com.todoseventos.todos_eventos.dto.requestDTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DepositoRequestDTO {

    @NotNull(message = "É obrigatório informar o id do cliente.")
    private Integer idPessoa;

    @NotNull(message = "É obrigatório informar um valor de depósito.")
    @DecimalMin(value = "0.01", message = "O valor de depósito deve ser positivo!")
    private Double valor;
}
