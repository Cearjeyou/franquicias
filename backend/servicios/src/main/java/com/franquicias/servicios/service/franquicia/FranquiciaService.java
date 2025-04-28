package com.franquicias.servicios.service.franquicia;

import com.franquicias.servicios.dto.franquicia.FranquiciaDTO;
import com.franquicias.servicios.dto.franquicia.FranquiciaRequest;
import com.franquicias.servicios.dto.producto.ProductoDTO;
import com.franquicias.servicios.dto.producto.ProductoRequest;
import com.franquicias.servicios.dto.producto.ProductoSucursalDTO;
import com.franquicias.servicios.dto.sucursal.SucursalDTO;
import com.franquicias.servicios.dto.sucursal.SucursalRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranquiciaService {
    Mono<FranquiciaDTO> crearFranquicia(FranquiciaRequest franquiciaRequest);
    Mono<FranquiciaDTO> actualizarFranquicia(String id, FranquiciaRequest franquiciaRequest);
    Mono<SucursalDTO> crearSucursal(SucursalRequest sucursalRequest, String franquiciaId);
    Mono<SucursalDTO> actualizarSucursal(String sucursalId, SucursalRequest sucursalRequest, String franquiciaId);
    Mono<ProductoDTO> crearProducto(String sucursalId, String franquiciaId, ProductoRequest productoRequest);
    Mono<Void> eliminarProducto(String sucursalId, String franquiciaId, String productoId);
    Mono<ProductoDTO> actualizarStockProducto(String sucursalId, String franquiciaId, String productoId, ProductoRequest productoRequest);
    Mono<ProductoDTO> actualizarNombreProducto(String sucursalId, String franquiciaId, String productoId, ProductoRequest productoRequest);
    Flux<ProductoSucursalDTO> obtenerProductosConMayorStock(String franquiciaId);
}
