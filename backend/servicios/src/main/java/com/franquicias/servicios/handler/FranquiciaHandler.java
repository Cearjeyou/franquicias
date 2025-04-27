package com.franquicias.servicios.handler;

import com.franquicias.servicios.dto.franquicia.FranquiciaRequest;
import com.franquicias.servicios.dto.sucursal.SucursalRequest;
import com.franquicias.servicios.service.franquicia.FranquiciaService;
import com.mongodb.internal.connection.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FranquiciaHandler {
    private final FranquiciaService franquiciaService;

    public Mono<ServerResponse> crearFranquicia(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(FranquiciaRequest.class)
                .flatMap(franquiciaService::crearFranquicia)
                .flatMap(franquiciaDTO -> ServerResponse.ok().bodyValue(franquiciaDTO))
                .switchIfEmpty(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> actualizarFranquicia(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return serverRequest.bodyToMono(FranquiciaRequest.class)
                .flatMap(franquiciaRequest -> franquiciaService.actualizarFranquicia(id, franquiciaRequest))
                .flatMap(franquiciaDTO -> ServerResponse.ok().bodyValue(franquiciaDTO))
                .switchIfEmpty(ServerResponse.badRequest().build())
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }

    public Mono<ServerResponse> crearSucursal(ServerRequest serverRequest) {
        String idFranquicia = serverRequest.pathVariable("idFranquicia");
        return serverRequest.bodyToMono(SucursalRequest.class)
                .flatMap(sucursalRequest -> franquiciaService.crearSucursal(sucursalRequest, idFranquicia))
                .flatMap(sucursalDTO -> ServerResponse.ok().bodyValue(sucursalDTO))
                .switchIfEmpty(ServerResponse.badRequest().build())
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }

    public Mono<ServerResponse> actualizarSucursal(ServerRequest serverRequest) {
        String idFranquicia = serverRequest.pathVariable("idFranquicia");
        String idSucursal = serverRequest.pathVariable("idSucursal");
        return serverRequest.bodyToMono(SucursalRequest.class)
                .flatMap(sucursalRequest -> franquiciaService.actualizarSucursal(idSucursal, sucursalRequest, idFranquicia))
                .flatMap(sucursalDTO -> ServerResponse.ok().bodyValue(sucursalDTO))
                .switchIfEmpty(ServerResponse.badRequest().build())
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue(e.getMessage()));
    }
}
