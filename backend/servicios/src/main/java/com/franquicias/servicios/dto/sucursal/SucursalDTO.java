package com.franquicias.servicios.dto.sucursal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SucursalDTO {
    private String id;
    private String nombre;
    private String franquiciaId;
}
