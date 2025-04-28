package com.franquicias.servicios.dto.producto;

import com.franquicias.servicios.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {
    public ProductoDTO toDTO(Producto producto, String sucursalId, String franquiciaId) {
        return ProductoDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .stock(producto.getStock() != null ? producto.getStock() : 0)
                .sucursalId(sucursalId)
                .franquiciaId(franquiciaId)
                .build();
    }

    public Producto toEntity(ProductoRequest productoRequest) {
        return Producto.builder()
                .nombre(productoRequest.getNombre())
                .stock(productoRequest.getStock() != null ? productoRequest.getStock() : 0)
                .build();
    }
}
