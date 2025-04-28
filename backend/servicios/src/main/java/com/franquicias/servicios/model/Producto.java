package com.franquicias.servicios.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    private String nombre;

    private Integer stock;
}
