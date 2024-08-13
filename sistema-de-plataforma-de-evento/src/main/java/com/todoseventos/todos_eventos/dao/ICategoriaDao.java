package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.model.evento.CategoriaModel;

public interface ICategoriaDao {
    CategoriaModel procurarId(Integer idCategoria);
    CategoriaModel buscarNomeCategoria(String nomeCategoria);
}

