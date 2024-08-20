package com.todoseventos.todos_eventos.dto.requestDTO;

import com.todoseventos.todos_eventos.enuns.TipoClienteEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdministradorRequestDTO {

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    private String senha;

    private TipoClienteEnum tipo_pessoa;
}
