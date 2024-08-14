package com.todoseventos.todos_eventos.dao.impl;

import com.todoseventos.todos_eventos.dao.ITipoClienteDao;
import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.model.cliente.TipoCliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
class TipoClienteDaoImpl implements ITipoClienteDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public TipoCliente buscarPorNomeTipoPessoa(String nomeTipoPessoa) {
        String sql = "SELECT * FROM procurar_tipo_cliente_por_nome(?)";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(TipoCliente.class), nomeTipoPessoa);
        } catch (Exception e) {
            throw new CustomException(ExceptionMessages.ERRO_BUSCAR_POR_NOME + e.getMessage());
        }
    }
}
