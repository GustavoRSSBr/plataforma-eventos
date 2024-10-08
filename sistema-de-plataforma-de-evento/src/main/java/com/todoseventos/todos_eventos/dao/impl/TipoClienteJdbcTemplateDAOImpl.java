package com.todoseventos.todos_eventos.dao.impl;

import com.todoseventos.todos_eventos.dao.ITipoClienteJdbcTemplateDAO;
import com.todoseventos.todos_eventos.model.cliente.TipoCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
class TipoClienteJdbcTemplateDAOImpl implements ITipoClienteJdbcTemplateDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public TipoCliente buscarPorNomeTipoPessoa(String nomeTipoPessoa) {
        String sql = "SELECT * FROM procurar_tipo_cliente_por_nome(?)";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(TipoCliente.class), nomeTipoPessoa);
    }
}
