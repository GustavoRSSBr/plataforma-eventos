package com.todoseventos.todos_eventos.dto.responseDTO;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
public class CustomExceptionResponseDTO {

    private String message;

    public CustomExceptionResponseDTO(String message) {

        this.message = message;

    }
}
