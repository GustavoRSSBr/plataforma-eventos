package com.todoseventos.todos_eventos.dao.impl;

import com.todoseventos.todos_eventos.dao.IClienteJuridicaJdbcTemplateDAO;
import com.todoseventos.todos_eventos.model.cliente.ClienteJuridico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
class ClienteJuridicaJdbcTemplateDAOImpl implements IClienteJuridicaJdbcTemplateDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public ClienteJuridico salvarCliJuridico(ClienteJuridico pessoaJuridica) {
        String sql = "SELECT inserir_cliente_juridico(?, ?)";
        jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) ps -> {
            ps.setInt(1, pessoaJuridica.getIdPessoa());
            ps.setString(2, pessoaJuridica.getCnpj());
            ps.execute();
            return null;
        });
        return pessoaJuridica;
    }

    @Override
    @Transactional
    public ClienteJuridico atualizarJuridico(ClienteJuridico pessoaJuridica) {
        String sql = "SELECT atualizar_cliente_juridico(?, ?)";

        jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) ps -> {
            ps.setInt(1, pessoaJuridica.getIdPessoa());
            ps.setString(2, pessoaJuridica.getCnpj());
            ps.execute();
            return null;
        });
        return pessoaJuridica;

    }

    @Override
    @Transactional
    public ClienteJuridico procurarCnpj(String cnpj) {
        String sql = "SELECT * FROM procurar_cliente_juridico_por_cnpj(?)";

        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ClienteJuridico.class), cnpj);
    }
}
