package com.franquicias.servicios.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "franquicias")
public class Franquicia {
    @Id
    private String id;

    private String nombre;

    @Builder.Default
    private List<Sucursal> sucursales = new ArrayList<>();
}
