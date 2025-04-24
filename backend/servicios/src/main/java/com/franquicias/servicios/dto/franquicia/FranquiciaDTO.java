package com.franquicias.servicios.dto.franquicia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FranquiciaDTO {
    private String id;
    private String nombre;
}
