package com.franquicias.servicios.dto.franquicia;

import com.franquicias.servicios.model.Franquicia;
import org.springframework.stereotype.Component;

@Component
public class FranquicaMapper {
    public FranquiciaDTO toDTO(Franquicia franquicia) {
        return FranquiciaDTO.builder()
                .id(franquicia.getId())
                .nombre(franquicia.getNombre())
                .build();
    }

    public Franquicia toEntity(FranquiciaRequest franquiciaRequest) {
        return Franquicia.builder()
                .nombre(franquiciaRequest.getNombre())
                .build();
    }
}
