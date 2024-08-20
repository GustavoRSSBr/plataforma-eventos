package com.todoseventos.todos_eventos.dao.impl;

import com.todoseventos.todos_eventos.dao.IEnderecoJdbcTemplateDAO;
import com.todoseventos.todos_eventos.model.evento.Endereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

@Repository
class EnderecoJdbcTemplateDAOImpl implements IEnderecoJdbcTemplateDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public Endereco salverEndereco(Endereco endereco) {
        String sql = "SELECT inserir_endereco(?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) ps -> {
            setPreparedStatementParameters(ps, endereco);
            ps.execute();
            return null;
        });
        return endereco;

    }


    @Override
    @Transactional
    public Endereco atualizarEndereco(Endereco endereco) {
        String sql = "SELECT atualizar_endereco(?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) ps -> {
            setPreparedStatementParameters(ps, endereco);
            ps.execute();
            return null;
        });
        return endereco;

    }

    @Override
    @Transactional
    public Optional<Endereco> procurarPorIdEvento(Integer id) {
        String sql = "SELECT * FROM procurar_endereco_por_id_evento(?)";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Endereco.class), id));
    }

    @Override
    @Transactional
    public void deletarPorIdEvento(Integer idEvento) {
        String sql = "SELECT deletar_endereco(?)";

        jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) ps -> {
            ps.setInt(1, idEvento);
            ps.execute();
            return null;
        });

    }

    private void setPreparedStatementParameters(PreparedStatement ps, Endereco endereco) throws SQLException {
        ps.setString(1, endereco.getRua());
        ps.setString(2, endereco.getNumero());
        ps.setString(3, endereco.getBairro());
        ps.setString(4, endereco.getCidade());
        ps.setString(5, endereco.getUf());
        ps.setString(6, endereco.getCep());
        ps.setInt(7, endereco.getIdEvento());
    }
}
