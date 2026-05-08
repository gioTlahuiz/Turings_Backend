package com.turings.backend.repository;

import com.turings.backend.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallesPedidos extends JpaRepository<DetallesPedidos, Long> {
    DetallesPedidos findById(long id);
}
