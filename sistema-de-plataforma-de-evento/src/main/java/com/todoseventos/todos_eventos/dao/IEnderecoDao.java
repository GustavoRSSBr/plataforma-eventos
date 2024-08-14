package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.model.evento.Endereco;

import java.util.Optional;

public interface IEnderecoDao {
    Endereco salverEndereco(Endereco endereco);
    Endereco atualizarEndereco(Endereco endereco);
    Optional<Endereco> procurarPorIdEvento(Integer id);
    void deletarPorIdEvento(Integer idEvento);
}

