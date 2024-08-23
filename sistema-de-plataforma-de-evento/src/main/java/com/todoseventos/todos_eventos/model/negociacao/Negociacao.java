package com.todoseventos.todos_eventos.model.negociacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Classe que representa uma negociação de ingresso.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Negociacao {

    private Integer idEvento;
    private Integer idPessoa;
}
