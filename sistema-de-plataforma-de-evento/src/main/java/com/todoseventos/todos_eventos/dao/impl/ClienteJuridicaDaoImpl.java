package com.todoseventos.todos_eventos.dao.impl;

import com.todoseventos.todos_eventos.dao.IClienteJuridicaDao;
import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.model.cliente.ClienteJuridicaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
class ClienteJuridicaDaoImpl implements IClienteJuridicaDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public ClienteJuridicaModel salvarCliJuridico(ClienteJuridicaModel pessoaJuridica) {
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
    public ClienteJuridicaModel atualizarJuridico(ClienteJuridicaModel pessoaJuridica) {
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
    public ClienteJuridicaModel procurarCnpj(String cnpj) {
        String sql = "SELECT * FROM procurar_cliente_juridico_por_cnpj(?)";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ClienteJuridicaModel.class), cnpj);
        } catch (Exception e) {
            throw new CustomException(ExceptionMessages.ERRO_BUSCAR_CLIENTE_CNPJ + e.getMessage());
        }
    }
}
