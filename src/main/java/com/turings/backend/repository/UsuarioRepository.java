package com.turings.backend.repository;

import com.turings.backend.model.Categoria;
import com.turings.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findById(long id);
    Usuario findByCorreoElectronico(String correo);
}
