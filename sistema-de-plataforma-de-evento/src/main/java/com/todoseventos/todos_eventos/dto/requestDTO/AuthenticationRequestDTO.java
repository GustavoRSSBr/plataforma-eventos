package com.todoseventos.todos_eventos.dto.requestDTO;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequestDTO {

    private String email;
    private String senha;

}
