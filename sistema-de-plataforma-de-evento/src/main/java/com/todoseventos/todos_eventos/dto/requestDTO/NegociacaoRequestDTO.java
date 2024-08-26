package com.todoseventos.todos_eventos.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * A classe NegociacaoRequestDTO representa um objeto de transferência de dados (DTO)
 * utilizado para realizar negociações de ingressos em eventos.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NegociacaoRequestDTO {

    private Integer idEvento;
    private Integer idPessoa;
    private String tipoIngresso;
}
