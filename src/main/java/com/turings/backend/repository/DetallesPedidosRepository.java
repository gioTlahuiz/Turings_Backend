package com.turings.backend.repository;

import com.turings.backend.model.DetallesPedidos;
import com.turings.backend.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DetallesPedidosRepository extends JpaRepository<DetallesPedidos, Long> {
    DetallesPedidos findById(long id);

    @Query("""
        SELECT dp
        FROM detalles_pedidos dp
        JOIN dp.pedido p
        JOIN dp.producto pr
        JOIN p.usuario u
            WHERE u.id_usuario = :id
            """)
    List<DetallesPedidos> getHistorial(@Param("id") Long id);



    @Query("""
        SELECT pr
        FROM detalles_pedidos dp
        JOIN dp.pedido p
        JOIN dp.producto pr
        JOIN p.usuario u
        GROUP BY pr.id_producto
    """)
    Page<Producto> getLast(Pageable pageable);

}
