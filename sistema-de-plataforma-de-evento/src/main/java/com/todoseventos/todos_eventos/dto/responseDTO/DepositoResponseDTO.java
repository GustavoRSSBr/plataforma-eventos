package com.todoseventos.todos_eventos.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositoResponseDTO {

    private String mensagem;
    private Double saldoAtualizado;
}
