package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.model.evento.Categoria;

public interface ICategoriaJdbcTemplateDAO {
    Categoria procurarId(Integer idCategoria);
    Categoria buscarNomeCategoria(String nomeCategoria);
}

