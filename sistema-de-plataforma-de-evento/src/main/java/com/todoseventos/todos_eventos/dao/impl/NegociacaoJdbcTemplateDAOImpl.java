package com.todoseventos.todos_eventos.dao.impl;

import com.todoseventos.todos_eventos.dao.INegociacaoJdbcTemplateDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcAccessor;
import org.springframework.stereotype.Repository;

/**
 * Implementação da interface INegociacaoJdbcTemplateDAO utilizando JdbcTemplate.
 */
@Repository
public class NegociacaoJdbcTemplateDAOImpl implements INegociacaoJdbcTemplateDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Método para comprar um ingresso.
     *
     * @param idEvento Identificador único do evento.
     * @param idPessoa Identificador único da pessoa.
     * @param tipoIngresso Tipo de ingresso para o evento.
     * @return String Resultado da compra do ingresso.
     */
    @Override
    public String comprarIngresso(int idEvento, int idPessoa, String tipoIngresso) {
        String sql = "SELECT comprar_ingresso(?, ?, ?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{idEvento, idPessoa, tipoIngresso}, String.class);
    }
}
