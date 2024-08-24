package com.todoseventos.todos_eventos.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositoRequestDTO {

    private Integer idPessoa;
    private Double valor;
}
