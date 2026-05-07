package com.turings.backend.repository;

import com.turings.backend.model.Producto;
import com.turings.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Usuario findById(long id);
}
