package com.todoseventos.todos_eventos.dao.impl;

import com.todoseventos.todos_eventos.dao.ICategoriaJdbcTemplateDAO;
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
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Categoria.class), idCategoria);
    }

    @Override
    @Transactional
    public Categoria buscarNomeCategoria(String nomeCategoria) {
        String sql = "SELECT * FROM procurar_categoria_por_nome(?)";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Categoria.class), nomeCategoria);
    }
}
