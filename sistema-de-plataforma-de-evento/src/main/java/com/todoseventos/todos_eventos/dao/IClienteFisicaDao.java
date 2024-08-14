package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.model.cliente.ClienteFisico;

public interface IClienteFisicaDao {
    ClienteFisico salvarCliFisico(ClienteFisico pessoaFisica);
    ClienteFisico atualizarCliFisico(ClienteFisico pessoaFisica);
    ClienteFisico procurarCpf(String cpf);
}
