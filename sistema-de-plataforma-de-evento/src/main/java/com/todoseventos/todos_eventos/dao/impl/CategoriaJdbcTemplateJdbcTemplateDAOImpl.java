package com.todoseventos.todos_eventos.dao.impl;

import com.todoseventos.todos_eventos.dao.ICategoriaJdbcTemplateDAO;
import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.model.evento.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
class CategoriaJdbcTemplateJdbcTemplateDAOImpl implements ICategoriaJdbcTemplateDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public Categoria procurarId(Integer idCategoria) {
        String sql = "SELECT * FROM procurar_categoria_por_id(?)";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Categoria.class), idCategoria);
        } catch (Exception e) {
            throw new CustomException(ExceptionMessages.ERRO_BUSCAR_POR_ID + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Categoria buscarNomeCategoria(String nomeCategoria) {
        String sql = "SELECT * FROM procurar_categoria_por_nome(?)";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Categoria.class), nomeCategoria);
        } catch (Exception e) {
            throw new CustomException(ExceptionMessages.ERRO_BUSCAR_POR_NOME + e.getMessage());
        }
    }
}
