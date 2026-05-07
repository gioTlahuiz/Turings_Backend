package com.turings.backend.repository;

import com.turings.backend.model.Categoria;
import com.turings.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Categoria, Long> {
    Usuario findById(long id);
}
