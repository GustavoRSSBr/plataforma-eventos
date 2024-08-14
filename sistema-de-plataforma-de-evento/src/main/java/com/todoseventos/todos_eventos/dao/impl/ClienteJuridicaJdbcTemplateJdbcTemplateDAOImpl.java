package com.todoseventos.todos_eventos.dao.impl;

import com.todoseventos.todos_eventos.dao.IClienteJuridicaJdbcTemplateDAO;
import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.model.cliente.ClienteJuridico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
class ClienteJuridicaJdbcTemplateJdbcTemplateDAOImpl implements IClienteJuridicaJdbcTemplateDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public ClienteJuridico salvarCliJuridico(ClienteJuridico pessoaJuridica) {
        String sql = "SELECT inserir_cliente_juridico(?, ?)";
        try {
            jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) ps -> {
                ps.setInt(1, pessoaJuridica.getIdPessoa());
                ps.setString(2, pessoaJuridica.getCnpj());
                ps.execute();
                return null;
            });
            return pessoaJuridica;
        } catch (Exception e) {
            throw new CustomException(ExceptionMessages.ERRO_SALVAR + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ClienteJuridico atualizarJuridico(ClienteJuridico pessoaJuridica) {
        String sql = "SELECT atualizar_cliente_juridico(?, ?)";
        try {
            jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) ps -> {
                ps.setInt(1, pessoaJuridica.getIdPessoa());
                ps.setString(2, pessoaJuridica.getCnpj());
                ps.execute();
                return null;
            });
            return pessoaJuridica;
        } catch (Exception e) {
            throw new CustomException(ExceptionMessages.ERRO_ATUALIZAR + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ClienteJuridico procurarCnpj(String cnpj) {
        String sql = "SELECT * FROM procurar_cliente_juridico_por_cnpj(?)";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ClienteJuridico.class), cnpj);
        } catch (Exception e) {
            throw new CustomException(ExceptionMessages.ERRO_BUSCAR_CLIENTE_CNPJ + e.getMessage());
        }
    }
}
