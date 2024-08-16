package com.todoseventos.todos_eventos.dao;

import com.todoseventos.todos_eventos.model.cliente.Cliente;
import java.util.List;

public interface IClienteJdbcTemplateDAO {
    Cliente procurarPorCpf(String cpf);
    Cliente procurarPorCnpj(String cnpj);
    Cliente procurarPorEmail(String email);
    Cliente salvarCliente(Cliente pessoa);
    Cliente atualizarCliente(Cliente pessoa);
    List<Cliente> listarTodasPessoas();
}
