package com.todoseventos.todos_eventos.dao.impl;

import com.todoseventos.todos_eventos.dao.IEmailJdbcTemplateDAO;
import com.todoseventos.todos_eventos.model.evento.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class EmailJdbcTemplateDAOImpl implements IEmailJdbcTemplateDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Transactional
    public List<Email> localizarPorIdEvento(Integer idEvento) {
        String sql = "SELECT * FROM buscar_pessoas_por_evento(?)";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Email.class), idEvento);
    }


}
