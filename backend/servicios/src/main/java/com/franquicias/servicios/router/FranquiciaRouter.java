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
                .PATCH("/franquicias/{id}", franquiciaHandler::actualizarFranquicia)
                .POST("/franquicias/{idFranquicia}/sucursales", franquiciaHandler::crearSucursal)
                .PATCH("/franquicias/{idFranquicia}/sucursales/{idSucursal}", franquiciaHandler::actualizarSucursal)
                .POST("/franquicias/{idFranquicia}/sucursales/{idSucursal}/productos", franquiciaHandler::crearProducto)
                .DELETE("/franquicias/{idFranquicia}/sucursales/{idSucursal}/productos/{idProducto}", franquiciaHandler::eliminarProducto)
                .PATCH("/franquicias/{idFranquicia}/sucursales/{idSucursal}/productos/{idProducto}/stock", franquiciaHandler::actualizarStockProducto)
                .PATCH("/franquicias/{idFranquicia}/sucursales/{idSucursal}/productos/{idProducto}/nombre", franquiciaHandler::actualizarNombreProducto)
                .GET("/franquicias/{idFranquicia}/productos-mayor-stock", franquiciaHandler::obtenerProductosConMayorStock)
                .build();
    }
}
