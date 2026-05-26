package com.turings.backend.repository;

import com.turings.backend.model.Categoria;
import com.turings.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findById(long id);

    Optional<Usuario> findByCorreoElectronico(String correo);
    Optional<Usuario> findByNombre(String nombre);

}
