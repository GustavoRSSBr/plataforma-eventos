package com.todoseventos.todos_eventos.dto.responseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcessResponseDTO {

    //Retornar o usuario e liberações(authorities)

    private String token;

    public AcessResponseDTO(String token){
        super();
        this.token = token;
    }
}
