package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.dto.requestDTO.NegociacaoRequestDTO;

/**
 * Interface para operações de negociação de ingressos utilizando JdbcTemplate.
 */
public interface INegociacaoJdbcTemplateDAO {

    /**
     * Interface a ser implementada para comprar um ingresso.
     */
    String comprarIngresso(NegociacaoRequestDTO request);
}
