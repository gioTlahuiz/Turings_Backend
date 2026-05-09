package com.turings.backend.repository;

import com.turings.backend.model.DetallesPedidos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallesPedidosRepository extends JpaRepository<DetallesPedidos, Long> {
    DetallesPedidos findById(long id);
}
