package com.franquicias.servicios.service.franquicia;

import com.franquicias.servicios.dto.franquicia.FranquiciaDTO;
import com.franquicias.servicios.dto.franquicia.FranquiciaRequest;
import com.franquicias.servicios.dto.sucursal.SucursalDTO;
import com.franquicias.servicios.dto.sucursal.SucursalRequest;
import reactor.core.publisher.Mono;

public interface FranquiciaService {
    Mono<FranquiciaDTO> crearFranquicia(FranquiciaRequest franquiciaRequest);
    Mono<FranquiciaDTO> actualizarFranquicia(String id, FranquiciaRequest franquiciaRequest);
    Mono<SucursalDTO> crearSucursal(SucursalRequest sucursalRequest, String franquiciaId);
    Mono<SucursalDTO> actualizarSucursal(String sucursalId, SucursalRequest sucursalRequest, String franquiciaId);
}
