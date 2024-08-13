package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.model.evento.EnderecoModel;

import java.util.Optional;

public interface IEnderecoDao {
    EnderecoModel salverEndereco(EnderecoModel endereco);
    EnderecoModel atualizarEndereco(EnderecoModel endereco);
    Optional<EnderecoModel> procurarPorIdEvento(Integer id);
    void deletarPorIdEvento(Integer idEvento);
}

