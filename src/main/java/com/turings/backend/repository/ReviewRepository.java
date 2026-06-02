package com.turings.backend.repository;

import com.turings.backend.model.Categoria;
import com.turings.backend.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findById(long id);

    @Query("""
        SELECT r
        FROM reviews r
        JOIN r.pedido p
        JOIN p.usuario u
        WHERE u.id_usuario = :id
    """)
    List<Review> findByUserId(@Param("id") long id);
}

