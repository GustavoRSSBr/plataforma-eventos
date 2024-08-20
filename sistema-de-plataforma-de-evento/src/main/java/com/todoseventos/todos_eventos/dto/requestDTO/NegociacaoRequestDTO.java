package com.todoseventos.todos_eventos.dto.requestDTO;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NegociacaoRequestDTO {

    private Integer idEvento;
    private Integer idPessoa;
    private String tipoIngresso;
}
