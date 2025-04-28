package com.franquicias.servicios.service.franquicia.impl;

import com.franquicias.servicios.dto.franquicia.FranquicaMapper;
import com.franquicias.servicios.dto.franquicia.FranquiciaDTO;
import com.franquicias.servicios.dto.franquicia.FranquiciaRequest;
import com.franquicias.servicios.dto.producto.ProductoDTO;
import com.franquicias.servicios.dto.producto.ProductoMapper;
import com.franquicias.servicios.dto.producto.ProductoRequest;
import com.franquicias.servicios.dto.producto.ProductoSucursalDTO;
import com.franquicias.servicios.dto.sucursal.SucursalDTO;
import com.franquicias.servicios.dto.sucursal.SucursalMapper;
import com.franquicias.servicios.dto.sucursal.SucursalRequest;
import com.franquicias.servicios.model.Franquicia;
import com.franquicias.servicios.model.Producto;
import com.franquicias.servicios.model.Sucursal;
import com.franquicias.servicios.repository.FranquiciaRepository;
import com.franquicias.servicios.service.franquicia.FranquiciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FranquiciaServiceImpl implements FranquiciaService {

    private final FranquiciaRepository franquiciaRepository;
    private final FranquicaMapper franquicaMapper;
    private final SucursalMapper sucursalMapper;
    private final ProductoMapper productoMapper;
    private final ReactiveMongoTemplate mongoTemplate;

    /**
     * Función para crear una franquicia.
     *
     * @param franquiciaRequest Objeto que contiene los datos de la franquicia a crear.
     * @return Mono<FranquiciaDTO> Objeto que contiene los datos de la franquicia creada.
     */
    @Override
    public Mono<FranquiciaDTO> crearFranquicia(FranquiciaRequest franquiciaRequest) {
        Franquicia franquicia = franquicaMapper.toEntity(franquiciaRequest);
        return franquiciaRepository.save(franquicia)
                .map(franquicaMapper::toDTO);
    }

    /**
     * Función para actualizar la información de una franquicia, como el nombre.
     *
     * @param id identificador de la franquicia en la base de datos
     * @param franquiciaRequest Objeto que contiene los datos de la franquicia a actualizar.
     * @return Mono<FranquiciaDTO> Objeto que contiene los datos de la franquicia actualizada.
     */
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

    /**
     * Función para crear una sucursal y asignarla a una franquicia.
     *
     * @param sucursalRequest Objeto que contiene los datos de la sucursal a crear.
     * @param franquiciaId Identificador de la franquicia a la que se asignará la sucursal.
     * @return Mono<SucursalDTO> Objeto que contiene los datos de la sucursal creada.
     */
    @Override
    public Mono<SucursalDTO> crearSucursal(SucursalRequest sucursalRequest, String franquiciaId) {
        return franquiciaRepository.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new RuntimeException("Franquicia no encontrada")))
                .flatMap(franquicia -> {
                    Sucursal sucursal = sucursalMapper.toEntity(sucursalRequest);
                    franquicia.getSucursales().add(sucursal);
                    return franquiciaRepository.save(franquicia)
                            .map(franquiciaGuardada -> sucursalMapper.toDTO(sucursal, franquiciaId));
                });
    }

    /**
     * Función para actualizar la información de una sucursal, como el nombre.
     *
     * @param sucursalId identificador de la sucursal en la base de datos
     * @param sucursalRequest Objeto que contiene los datos de la sucursal a actualizar.
     * @param franquiciaId Identificador de la franquicia a la que pertenece la sucursal.
     * @return Mono<SucursalDTO> Objeto que contiene los datos de la sucursal actualizada.
     */
    @Override
    public Mono<SucursalDTO> actualizarSucursal(String sucursalId, SucursalRequest sucursalRequest, String franquiciaId) {
        return franquiciaRepository.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new RuntimeException("Franquicia no encontrada")))
                .flatMap(franquicia -> {
                    Sucursal sucursal = franquicia.getSucursales().stream()
                            .filter(s -> s.getId().equals(sucursalId))
                            .findFirst().orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

                    sucursal.setNombre(sucursalRequest.getNombre());

                    return franquiciaRepository.save(franquicia)
                            .map(franquiciaGuardada -> sucursalMapper.toDTO(sucursal, franquiciaId));

                });
    }

    /**
     * Función para crear un producto y asignarlo a una sucursal de una franquicia.
     *
     * @param sucursalId identificador de la sucursal en la base de datos a la que pertenceria el producto
     * @param franquiciaId identificador de la franquicia a la que pertenece la sucursal
     * @param productoRequest Objeto que contiene la información del producto a crear.
     * @return Mono<ProductoDTO> Objeto que contiene los datos del producto creado.
     */
    @Override
    public Mono<ProductoDTO> crearProducto(String sucursalId, String franquiciaId, ProductoRequest productoRequest) {
        return franquiciaRepository.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new RuntimeException("Franquicia no encontrada")))
                .flatMap(franquicia -> {
                    Sucursal sucursal = franquicia.getSucursales().stream()
                            .filter(s -> s.getId().equals(sucursalId))
                            .findFirst().orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));
                    Producto producto = productoMapper.toEntity(productoRequest);
                    sucursal.getProductos().add(producto);

                    return franquiciaRepository.save(franquicia)
                            .map(franquiciaGuardada -> productoMapper.toDTO(producto, sucursalId, franquiciaId));
                });
    }

    /**
     * Función para eliminar un producto de una sucursal de una franquicia.
     *
     * @param sucursalId identificador de la sucursal en la base de datos a la que pertenece el producto
     * @param franquiciaId identificador de la franquicia a la que pertenece la sucursal
     * @param productoId identificador del producto en la base de datos
     * @return Mono<Void> Mono vacío si la operación se realiza correctamente.
     */
    @Override
    public Mono<Void> eliminarProducto(String sucursalId, String franquiciaId, String productoId) {
        return franquiciaRepository.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new RuntimeException("Franquicia no encontrada")))
                .flatMap(franquicia -> {
                    Sucursal sucursal = franquicia.getSucursales().stream()
                            .filter(s -> s.getId().equals(sucursalId))
                            .findFirst().orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

                    Producto producto = sucursal.getProductos().stream()
                            .filter(p -> p.getId().equals(productoId))
                            .findFirst().orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                    sucursal.getProductos().remove(producto);
                    return franquiciaRepository.save(franquicia).then();
                });
    }

    /**
     * Función para actualizar el stock de un producto en una sucursal de una franquicia.
     *
     * @param sucursalId identificador de la sucursal en la base de datos a la que pertenece el producto
     * @param franquiciaId identificador de la franquicia a la que pertenece la sucursal
     * @param productoId identificador del producto en la base de datos
     * @param productoRequest Objeto que contiene la información del producto a actualizar.
     * @return Mono<ProductoDTO> Objeto que contiene los datos del producto actualizado.
     */
    @Override
    public Mono<ProductoDTO> actualizarStockProducto(String sucursalId, String franquiciaId, String productoId, ProductoRequest productoRequest) {
        return franquiciaRepository.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new RuntimeException("Franquicia no encontrada")))
                .flatMap(franquicia -> {
                    Sucursal sucursal = franquicia.getSucursales().stream()
                            .filter(s -> s.getId().equals(sucursalId))
                            .findFirst().orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

                    Producto producto = sucursal.getProductos().stream()
                            .filter(p -> p.getId().equals(productoId))
                            .findFirst().orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                    producto.setStock(productoRequest.getStock());
                    return franquiciaRepository.save(franquicia)
                            .map(franquiciaGuardada -> productoMapper.toDTO(producto, sucursalId, franquiciaId));
                });
    }

    /**
     * Función para actualizar el nombre de un producto en una sucursal de una franquicia.
     *
     * @param sucursalId identificador de la sucursal en la base de datos a la que pertenece el producto
     * @param franquiciaId identificador de la franquicia a la que pertenece la sucursal
     * @param productoId identificador del producto en la base de datos
     * @param productoRequest Objeto que contiene la información del producto a actualizar.
     * @return Mono<ProductoDTO> Objeto que contiene los datos del producto actualizado.
     */
    @Override
    public Mono<ProductoDTO> actualizarNombreProducto(String sucursalId, String franquiciaId, String productoId, ProductoRequest productoRequest) {
        return franquiciaRepository.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new RuntimeException("Franquicia no encontrada")))
                .flatMap(franquicia -> {
                    Sucursal sucursal = franquicia.getSucursales().stream()
                            .filter(s -> s.getId().equals(sucursalId))
                            .findFirst().orElseThrow(() -> new RuntimeException("Sucursal no encontrada"));

                    Producto producto = sucursal.getProductos().stream()
                            .filter(p -> p.getId().equals(productoId))
                            .findFirst().orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                    producto.setNombre(productoRequest.getNombre());
                    return franquiciaRepository.save(franquicia)
                            .map(franquiciaGuardada -> productoMapper.toDTO(producto, sucursalId, franquiciaId));
                });
    }

    /**
     * Función para obtener una lista de productos con el stock máximo por sucursal de una franquicia.
     *
     * @param franquiciaId identificador de la franquicia en la base de datos
     * @return Flux<ProductoSucursalDTO> Objeto que contiene los datos de los productos con el stock máximo por sucursal.
     */
    @Override
    public Flux<ProductoSucursalDTO> obtenerProductosConMayorStock(String franquiciaId) {
        return franquiciaRepository.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new RuntimeException("Franquicia no encontrada")))
                .flatMapMany((franquicia -> franquiciaRepository.findProductosConMaxStockPorSucursal(franquiciaId)));
    }


}
