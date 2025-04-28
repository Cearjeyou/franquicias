package com.franquicias.servicios.repository;

import com.franquicias.servicios.dto.producto.ProductoSucursalDTO;
import com.franquicias.servicios.model.Franquicia;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface FranquiciaRepository extends ReactiveMongoRepository<Franquicia, String> {
    @Aggregation(pipeline = {
            "{ $match: { _id: ?0 } }",
            "{ $unwind: '$sucursales' }",
            "{ $unwind: '$sucursales.productos' }",
            "{ $sort: { 'sucursales.productos.stock': -1 } }",
            "{ $group: { " +
                    "_id: { sucursalId: '$sucursales._id', stock: '$sucursales.productos.stock' }, " +
                    "sucursalNombre: { $first: '$sucursales.nombre' }, " +
                    "productos: { $push: { " +
                    "id: '$sucursales.productos._id', " +
                    "nombre: '$sucursales.productos.nombre', " +
                    "stock: '$sucursales.productos.stock' " +
                    "} } " +
                    "} }",
            "{ $group: { " +
                    "_id: '$_id.sucursalId', " +
                    "sucursal: { $first: '$sucursalNombre' }, " +
                    "maxStock: { $max: '$_id.stock' }, " +
                    "productos: { $first: '$productos' } " +
                    "} }",
            "{ $unwind: '$productos' }",
            "{ $match: { $expr: { $eq: ['$productos.stock', '$maxStock'] } } }",
            "{ $project: { " +
                    "_id: 0, " +
                    "sucursalId: '$_id', " +
                    "sucursal: 1, " +
                    "id: '$productos.id', " +
                    "nombre: '$productos.nombre', " +
                    "stock: '$productos.stock' " +
                    "} }"
    })
    Flux<ProductoSucursalDTO> findProductosConMaxStockPorSucursal(String franquiciaId);
}
