package com.todoseventos.todos_eventos.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigSwagger {

    @Bean
    public OpenAPI configOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .version("1.0.0")
                        .title("API Sistema para Plataforma de Eventos (SPDE)")
                        .description("Documentação da API para o Sistema para Plataforma de Eventos (SPDE). " +
                                "Gerencie eventos de forma eficiente: crie, edite, exclua eventos, " +
                                "registre participantes, emita ingressos e envie notificações automáticas."));
    }
}

   //http://localhost:27031/swagger-ui/index.html#/
