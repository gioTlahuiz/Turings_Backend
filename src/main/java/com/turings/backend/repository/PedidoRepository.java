package com.turings.backend.repository;

import com.turings.backend.model.Categoria;
import com.turings.backend.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{
    Pedido findById(long id);
}
