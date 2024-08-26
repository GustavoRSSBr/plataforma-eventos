package com.todoseventos.todos_eventos.usecase;

import com.todoseventos.todos_eventos.dao.IClienteJdbcTemplateDAO;
import com.todoseventos.todos_eventos.model.cliente.Administrador;
import com.todoseventos.todos_eventos.model.cliente.Cliente;
import com.todoseventos.todos_eventos.validador.validadoresAtributo.ValidadorEmail;
import com.todoseventos.todos_eventos.validador.validadoresAtributo.ValidadorSenha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdministradorService {

    @Autowired
    private IClienteJdbcTemplateDAO clienteJdbcTemplateDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void salvarAdministrador(Administrador administrador) {

        ValidadorEmail.validarEmail(administrador.getEmail());
        ValidadorSenha.validarSenha(administrador.getSenha());

        String senhaCriptografada = passwordEncoder.encode(administrador.getSenha());

        Cliente cliente = Cliente.builder()
                .nome("Administrador")
                .email(administrador.getEmail())
                .senha(senhaCriptografada)
                .telefone("0000000000")
                .tipo_pessoa(3)
                .build();

        clienteJdbcTemplateDAO.salvarCliente(cliente);
    }
}

