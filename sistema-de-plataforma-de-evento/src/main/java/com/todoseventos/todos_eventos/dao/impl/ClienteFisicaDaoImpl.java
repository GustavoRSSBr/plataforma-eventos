package com.todoseventos.todos_eventos.dao.impl;

import com.todoseventos.todos_eventos.dao.IClienteFisicaDao;
import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.model.cliente.ClienteFisicaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
class ClienteFisicaDaoImpl implements IClienteFisicaDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public ClienteFisicaModel salvarCliFisico(ClienteFisicaModel pessoaFisica) {
        String sql = "SELECT inserir_cliente_fisico(?, ?, ?)";
        try {
            jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) ps -> {
                ps.setInt(1, pessoaFisica.getIdPessoa());
                ps.setString(2, pessoaFisica.getCpf());
                ps.setString(3, pessoaFisica.getDataNascimento());
                ps.execute();
                return null;
            });
            return pessoaFisica;
        } catch (Exception e) {
            throw new CustomException(ExceptionMessages.ERRO_SALVAR + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ClienteFisicaModel atualizarCliFisico(ClienteFisicaModel pessoaFisica) {
        String sql = "SELECT atualizar_cliente_fisico(?, ?, ?)";
        try {
            jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) ps -> {
                ps.setInt(1, pessoaFisica.getIdPessoa());
                ps.setString(2, pessoaFisica.getCpf());
                ps.setString(3, pessoaFisica.getDataNascimento());
                ps.execute();
                return null;
            });
            return pessoaFisica;
        } catch (Exception e) {
            throw new CustomException(ExceptionMessages.ERRO_ATUALIZAR + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ClienteFisicaModel procurarCpf(String cpf) {
        String sql = "SELECT * FROM procurar_cliente_fisico_por_cpf(?)";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ClienteFisicaModel.class), cpf);
        } catch (Exception e) {
            throw new CustomException(ExceptionMessages.ERRO_BUSCAR_CLIENTE_CPF + e.getMessage());
        }
    }
}
