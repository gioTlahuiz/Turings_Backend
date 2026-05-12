package com.turings.backend.service;

import com.turings.backend.model.DetallesPedidos;
import com.turings.backend.repository.DetallesPedidosRepository;

import java.util.List;
import java.util.Optional;

/***
 * @version 1.0
 * @since 1.0
 */

public class DetallesPedidosService {

    final private DetallesPedidosRepository detallesPedidosRepository;

    public DetallesPedidosService(DetallesPedidosRepository detallesPedidosRepository) {
        this.detallesPedidosRepository = detallesPedidosRepository;
    }

    //Metodo para encontrar todo los detalles de producto
    public List<DetallesPedidos> findAll() {
        return detallesPedidosRepository.findAll();
    }

    //Metodo para encontrar detalle de producto por ID
    public Optional<DetallesPedidos> findById(Long id) {
        return detallesPedidosRepository.findById(id);
    }

    //Metodo para guardar detalles de productos
    public DetallesPedidos save(DetallesPedidos detallesPedidos) {
        return detallesPedidosRepository.save(detallesPedidos);
    }

    //Metodo para eliminar detalles de productos por ID
    public Optional<DetallesPedidos> deleteById(Long id) {
        Optional<DetallesPedidos> detallesPedidosOptional = detallesPedidosRepository.findById(id);
        if (detallesPedidosOptional.isPresent()) {
            detallesPedidosRepository.deleteById(id);
            return detallesPedidosOptional;
        }
        return Optional.empty();
    }
}
