package com.franquicias.servicios.dto.sucursal;

import com.franquicias.servicios.model.Sucursal;
import org.springframework.stereotype.Component;

@Component
public class SucursalMapper {
    public SucursalDTO toDTO(Sucursal sucursal, String franquiciaId) {
        return SucursalDTO.builder()
                .id(sucursal.getId())
                .nombre(sucursal.getNombre())
                .franquiciaId(franquiciaId)
                .build();
    }

    public Sucursal toEntity(SucursalRequest sucursalRequest) {
        return Sucursal.builder()
                .nombre(sucursalRequest.getNombre())
                .build();
    }
}
