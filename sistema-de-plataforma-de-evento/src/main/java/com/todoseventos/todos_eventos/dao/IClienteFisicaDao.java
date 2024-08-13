package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.model.cliente.ClienteFisicaModel;

public interface IClienteFisicaDao {
    ClienteFisicaModel salvarCliFisico(ClienteFisicaModel pessoaFisica);
    ClienteFisicaModel atualizarCliFisico(ClienteFisicaModel pessoaFisica);
    ClienteFisicaModel procurarCpf(String cpf);
}
