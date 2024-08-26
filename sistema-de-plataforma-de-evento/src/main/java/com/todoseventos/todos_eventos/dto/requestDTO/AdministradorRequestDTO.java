package com.todoseventos.todos_eventos.dto.requestDTO;

import com.todoseventos.todos_eventos.enuns.TipoClienteEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdministradorRequestDTO {

    private String email;
    private String senha;
    private TipoClienteEnum tipo_pessoa;
}
