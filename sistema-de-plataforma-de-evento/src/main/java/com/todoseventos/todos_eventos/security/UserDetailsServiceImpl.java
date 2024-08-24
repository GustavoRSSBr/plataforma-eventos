package com.todoseventos.todos_eventos.security;

import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;
import com.todoseventos.todos_eventos.model.cliente.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional

    public UserDetails loadUserByUsername(String email) {
        Cliente user = jdbcTemplate.queryForObject(
                "SELECT * FROM buscar_pessoa_por_email(?)",
                new Object[]{email},
                (rs, rowNum) -> {
                    Cliente u = new Cliente();
                    u.setIdPessoa(rs.getInt("id_pessoa"));
                    u.setEmail(rs.getString("email"));
                    u.setSenha(rs.getString("senha"));
                    u.setTipo_pessoa(rs.getInt("id_tipo_pessoa"));

                    return u;
                }
        );

        if (user == null) {
            throw new CustomException(ExceptionMessages.TOKEN_EMAIL + email);
        }

        return UserDetailsModelImpl.build(user);
    }
}
