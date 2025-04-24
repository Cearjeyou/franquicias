package com.franquicias.servicios.service.franquicia.impl;

import com.franquicias.servicios.dto.franquicia.FranquicaMapper;
import com.franquicias.servicios.dto.franquicia.FranquiciaDTO;
import com.franquicias.servicios.dto.franquicia.FranquiciaRequest;
import com.franquicias.servicios.model.Franquicia;
import com.franquicias.servicios.repository.FranquiciaRepository;
import com.franquicias.servicios.service.franquicia.FranquiciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
@RequiredArgsConstructor
public class FranquiciaServiceImpl implements FranquiciaService {

    private final FranquiciaRepository franquiciaRepository;
    private final FranquicaMapper franquicaMapper;

    @Override
    public Mono<FranquiciaDTO> crearFranquicia(FranquiciaRequest franquiciaRequest) {
        Franquicia franquicia = franquicaMapper.toEntity(franquiciaRequest);
        return franquiciaRepository.save(franquicia)
                .map(franquicaMapper::toDTO);
    }

    @Override
    public Mono<FranquiciaDTO> actualizarFranquicia(String id, FranquiciaRequest franquiciaRequest) {
        return franquiciaRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Franquicia no encontrada")))
                .flatMap(franquicia -> {
                    franquicia.setNombre(franquiciaRequest.getNombre());
                    return franquiciaRepository.save(franquicia);
                })
                .map(franquicaMapper::toDTO);
    }
}
