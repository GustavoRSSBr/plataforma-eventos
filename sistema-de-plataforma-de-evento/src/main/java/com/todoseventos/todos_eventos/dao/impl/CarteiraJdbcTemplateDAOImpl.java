package com.todoseventos.todos_eventos.dao.impl;

import com.todoseventos.todos_eventos.dao.ICarteiraJdbcTemplateDAO;
import com.todoseventos.todos_eventos.model.carteira.Carteira;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CarteiraJdbcTemplateDAOImpl implements ICarteiraJdbcTemplateDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Double depositar(int idPessoa, double valor) {
        String sql = "SELECT depositar(?, ?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{idPessoa, valor}, Double.class);
    }

    @Override
    public void criarCarteira(Carteira carteira) {
        String sql = "INSERT INTO CARTEIRA (saldo, id_pessoa) VALUES (?, ?)";
        jdbcTemplate.update(sql, carteira.getSaldo(), carteira.getIdPessoa());
    }

    @Override
    public Double consultarSaldo(int idPessoa) {
        String sql = "SELECT saldo FROM CARTEIRA WHERE id_pessoa = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, idPessoa);
    }
}
