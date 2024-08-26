package com.todoseventos.todos_eventos.usecase;

import com.todoseventos.todos_eventos.dao.IClienteJdbcTemplateDAO;
import com.todoseventos.todos_eventos.model.cliente.Administrador;
import com.todoseventos.todos_eventos.model.cliente.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

class AdministradorServiceTest {

    @Mock
    private IClienteJdbcTemplateDAO iClienteJdbcTemplateDAO;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdministradorService administradorService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void salvarAdministradorSucess() {

        Administrador administrador = new Administrador(1L, "admTeste@gmail.com", "Senha@adm4");
        administrador.setEmail("admTeste@gmail.com");
        administrador.setSenha("Senha@adm4");

        when(passwordEncoder.encode(administrador.getSenha())).thenReturn("senhaCriptografada");

        administradorService.salvarAdministrador(administrador);

        verify(iClienteJdbcTemplateDAO, times(1)).salvarCliente(any(Cliente.class));
        verify(passwordEncoder, times(1)).encode(administrador.getSenha());
    }
}