package com.todoseventos.todos_eventos.dao.impl;

import com.todoseventos.todos_eventos.dao.IClienteFisicaJdbcTemplateDAO;
import com.todoseventos.todos_eventos.model.cliente.ClienteFisico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
class ClienteFisicaJdbcTemplateDAOImpl implements IClienteFisicaJdbcTemplateDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public ClienteFisico salvarCliFisico(ClienteFisico pessoaFisica) {
        String sql = "SELECT inserir_cliente_fisico(?, ?, ?)";
        jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) ps -> {
            ps.setInt(1, pessoaFisica.getIdPessoa());
            ps.setString(2, pessoaFisica.getCpf());
            ps.setString(3, pessoaFisica.getDataNascimento());
            ps.execute();
            return null;
        });
        return pessoaFisica;

    }

    @Override
    @Transactional
    public ClienteFisico atualizarCliFisico(ClienteFisico pessoaFisica) {
        String sql = "SELECT atualizar_cliente_fisico(?, ?, ?)";
        jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) ps -> {
            ps.setInt(1, pessoaFisica.getIdPessoa());
            ps.setString(2, pessoaFisica.getCpf());
            ps.setString(3, pessoaFisica.getDataNascimento());
            ps.execute();
            return null;
        });
        return pessoaFisica;
    }

    @Override
    @Transactional
    public ClienteFisico procurarCpf(String cpf) {
        String sql = "SELECT * FROM procurar_cliente_fisico_por_cpf(?)";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ClienteFisico.class), cpf);

    }
}
