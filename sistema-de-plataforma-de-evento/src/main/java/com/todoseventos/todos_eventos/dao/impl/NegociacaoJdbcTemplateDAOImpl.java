package com.todoseventos.todos_eventos.dao.impl;

import com.todoseventos.todos_eventos.dao.INegociacaoJdbcTemplateDAO;
import com.todoseventos.todos_eventos.dto.requestDTO.NegociacaoRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Implementação da interface INegociacaoJdbcTemplateDAO utilizando JdbcTemplate.
 */
@Repository
public class NegociacaoJdbcTemplateDAOImpl implements INegociacaoJdbcTemplateDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Método para comprar um ingresso..
     * @return String Resultado da compra do ingresso.
     */
    @Override
    public String comprarIngresso(NegociacaoRequestDTO request) {
        String sql = "SELECT comprar_ingresso(?, ?, ?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{request}, String.class);
    }
}
