package com.todoseventos.todos_eventos.model.cliente;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table("tipo_pessoa")
@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserDetailsModelImpl implements UserDetails {

    @Id
    private Integer id;
    private String email;
    private String password;
    private Integer role;
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Construtor da classe UserDetailsImpl.
     * @param id O ID do usuário.
     * @param email O e-mail do usuário.
     * @param password A senha do usuário.
     * @param authorities As autoridades (roles) do usuário.
     */

    public UserDetailsModelImpl(Integer id, String email, String password, Integer role, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.authorities = getAuthorities();
    }

    /**
     * Cria uma instância de UserDetailsImpl a partir de um objeto ClienteModel.
     * @param usuario O objeto ClienteModel contendo os detalhes do usuário.
     * @return Uma instância de UserDetailsImpl.
     */
    public static UserDetailsModelImpl build(Cliente usuario) {
        return new UserDetailsModelImpl(
                usuario.getIdPessoa(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getTipo_pessoa(),
                new ArrayList<>());
    }

    /**
     * Retorna as autoridades (roles) do usuário.
     * @return Uma coleção de GrantedAuthority representando as autoridades do usuário.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == 3) return  List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * Retorna a senha do usuário.
     * @return A senha do usuário.
     */

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Retorna o nome de usuário (e-mail).
     * @return O e-mail do usuário.
     */
    @Override
    public String getUsername() {
        return email;
    }

//    /**
//     * Retorna o ID do usuário.
//     * @return O ID do usuário.
//     */
//    public Integer getId() {
//        return id;
//    }

    /**
     * Indica se a conta do usuário está expirada.
     * @return true, se a conta não estiver expirada; false, caso contrário.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indica se a conta do usuário está bloqueada.
     * @return true, se a conta não estiver bloqueada; false, caso contrário.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indica se as credenciais do usuário estão expiradas.
     * @return true, se as credenciais não estiverem expiradas; false, caso contrário.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indica se a conta do usuário está habilitada.
     * @return true, se a conta estiver habilitada; false, caso contrário.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
