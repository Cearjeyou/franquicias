package com.franquicias.servicios.service.franquicia;

import com.franquicias.servicios.dto.franquicia.FranquiciaDTO;
import com.franquicias.servicios.dto.franquicia.FranquiciaRequest;
import reactor.core.publisher.Mono;

public interface FranquiciaService {
    Mono<FranquiciaDTO> crearFranquicia(FranquiciaRequest franquiciaRequest);
    Mono<FranquiciaDTO> actualizarFranquicia(String id, FranquiciaRequest franquiciaRequest);
}
