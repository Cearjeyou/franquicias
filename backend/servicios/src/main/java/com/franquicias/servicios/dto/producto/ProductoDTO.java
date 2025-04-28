package com.franquicias.servicios.dto.producto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {
    private String id;
    private String nombre;
    private Integer stock;
    private String sucursalId;
    private String franquiciaId;
}
