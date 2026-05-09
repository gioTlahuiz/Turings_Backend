package com.turings.backend.repository;

import com.turings.backend.model.Categoria;
import com.turings.backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findById(long id);
}

