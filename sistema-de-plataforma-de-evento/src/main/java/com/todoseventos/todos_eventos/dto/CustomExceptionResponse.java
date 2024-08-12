package com.todoseventos.todos_eventos.dto;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
public class CustomExceptionResponse {

    private String message;

    public CustomExceptionResponse(String message) {

        this.message = message;

    }
}
