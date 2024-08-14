package com.todoseventos.todos_eventos.dao.impl;

import com.todoseventos.todos_eventos.dao.IClienteDao;
import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.model.cliente.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
class ClienteDaoImpl implements IClienteDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public Cliente procurarPorCpf(String cpf) {
        String sql = "SELECT * FROM procurar_cliente_por_cpf(?)";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Cliente.class), cpf);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new CustomException(ExceptionMessages.ERRO_BUSCAR_CLIENTE_CPF + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Cliente procurarPorCnpj(String cnpj) {
        String sql = "SELECT * FROM procurar_cliente_por_cnpj(?)";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Cliente.class), cnpj);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new CustomException(ExceptionMessages.ERRO_BUSCAR_CLIENTE_CNPJ + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Cliente procurarPorEmail(String email) {
        String sql = "SELECT * FROM procurar_cliente_por_email(?)";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Cliente.class), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new CustomException(ExceptionMessages.ERRO_BUSCAR_USUARIO_POR_EMAIL + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Cliente salvarCliente(Cliente pessoa) {
        String sql = "SELECT inserir_cliente(?, ?, ?, ?, ?)";
        try {
            Integer idPessoa = jdbcTemplate.queryForObject(sql, new Object[]{
                    pessoa.getNome(), pessoa.getEmail(), pessoa.getSenha(), pessoa.getTelefone(), pessoa.getTipo_pessoa()
            }, Integer.class);
            pessoa.setIdPessoa(idPessoa);
            return pessoa;
        } catch (Exception e) {
            throw new CustomException(ExceptionMessages.ERRO_SALVAR_CLIENTE + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Cliente atualizarCliente(Cliente pessoa) {
        String sql = "SELECT atualizar_cliente(?, ?, ?, ?, ?, ?)";
        try {
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
        } catch (Exception e) {
            throw new CustomException(ExceptionMessages.ERRO_ATUALIZAR_CLIENTE + e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Cliente> listarTodasPessoas() {
        String sql = "SELECT * FROM listar_todos_clientes()";
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Cliente.class));
        } catch (Exception e) {
            throw new CustomException(ExceptionMessages.ERRO_LISTAR_TODOS + e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean existeEmail(String email){
        return procurarPorEmail(email) != null;
    }
}
