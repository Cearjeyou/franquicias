package com.franquicias.servicios.router;

import com.franquicias.servicios.handler.FranquiciaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class FranquiciaRouter {
    @Bean
    public RouterFunction<ServerResponse> franquiciaRoutes(FranquiciaHandler franquiciaHandler) {
        return RouterFunctions.route()
                .POST("/franquicias", franquiciaHandler::crearFranquicia)
                .PUT("/franquicias/{id}", franquiciaHandler::actualizarFranquicia)
                .build();
    }
}
