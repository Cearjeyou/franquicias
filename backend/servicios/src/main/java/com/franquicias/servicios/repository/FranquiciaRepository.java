package com.franquicias.servicios.repository;

import com.franquicias.servicios.model.Franquicia;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FranquiciaRepository extends ReactiveMongoRepository<Franquicia, String> {

}
