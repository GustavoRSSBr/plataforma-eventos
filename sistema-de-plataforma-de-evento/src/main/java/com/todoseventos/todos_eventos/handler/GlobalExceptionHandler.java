package com.todoseventos.todos_eventos.handler;

import com.todoseventos.todos_eventos.dto.responseDTO.CustomExceptionResponseDTO;
import com.todoseventos.todos_eventos.enuns.ExceptionMessages;
import com.todoseventos.todos_eventos.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomExceptionResponseDTO> handleCustomException(CustomException ex) {
        logger.error("CustomException: {}", ex.getMessage(), ex);
        CustomExceptionResponseDTO response = new CustomExceptionResponseDTO(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomExceptionResponseDTO> handleGenericException(Exception ex) {
        logger.error("GenericException: {}", ex.getMessage(), ex);
        CustomExceptionResponseDTO response = new CustomExceptionResponseDTO(ExceptionMessages.ERRO_INTERNO);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
