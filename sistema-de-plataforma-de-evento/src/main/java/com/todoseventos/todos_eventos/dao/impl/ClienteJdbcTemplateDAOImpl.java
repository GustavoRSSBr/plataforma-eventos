package com.todoseventos.todos_eventos.dao.impl;

import com.todoseventos.todos_eventos.dao.IClienteJdbcTemplateDAO;
import com.todoseventos.todos_eventos.model.cliente.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
class ClienteJdbcTemplateDAOImpl implements IClienteJdbcTemplateDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public Cliente procurarPorCpf(String cpf) {
        String sql = "SELECT * FROM procurar_cliente_por_cpf(?)";

        List<Cliente> clientes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Cliente.class), cpf);

        if (clientes.isEmpty()) {
            return null;
        } else {
            return clientes.get(0);
        }
    }

    @Override
    @Transactional
    public Cliente procurarPorCnpj(String cnpj) {
        String sql = "SELECT * FROM procurar_cliente_por_cnpj(?)";

        List<Cliente> clientes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Cliente.class), cnpj);
        if (clientes.isEmpty()) {
            return null;
        } else {
            return clientes.get(0);
        }

    }

    @Override
    @Transactional
    public Cliente procurarPorEmail(String email) {
        String sql = "SELECT * FROM procurar_cliente_por_email(?)";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Cliente.class), email);
    }

    @Override
    @Transactional
    public Cliente salvarCliente(Cliente pessoa) {
        String sql = "SELECT inserir_cliente(?, ?, ?, ?, ?)";
        Integer idPessoa = jdbcTemplate.queryForObject(sql, new Object[]{
                pessoa.getNome(), pessoa.getEmail(), pessoa.getSenha(), pessoa.getTelefone(), pessoa.getTipo_pessoa()
        }, Integer.class);
        pessoa.setIdPessoa(idPessoa);
        return pessoa;
    }

    @Override
    @Transactional
    public Cliente atualizarCliente(Cliente pessoa) {
        String sql = "SELECT atualizar_cliente(?, ?, ?, ?, ?, ?)";

        jdbcTemplate.execute(sql, (PreparedStatementCallback<Void>) ps -> {
            ps.setInt(1, pessoa.getIdPessoa());
            ps.setString(2, pessoa.getNome());
            ps.setString(3, pessoa.getEmail());
            ps.setString(4, pessoa.getSenha());
            ps.setString(5, pessoa.getTelefone());
            ps.setInt(6, pessoa.getTipo_pessoa());
            ps.execute();
            return null;
        });
        return pessoa;

    }

    @Override
    @Transactional
    public List<Cliente> listarTodasPessoas() {
        String sql = "SELECT * FROM listar_todos_clientes()";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Cliente.class));


    }
}

