package com.turings.backend.service;

import com.turings.backend.model.Usuario;
import com.turings.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService (UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getAllUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(Long id){
        return usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("El usuario con el id["+ id +"] no existe"));
    }

    public Usuario guardarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario (Long id, Usuario actualizarUsuario){
        Usuario usuario = usuarioRepository.findById(id).orElse(null);

        if (usuario != null){
            usuario.setNombre(actualizarUsuario.getNombre());
            usuario.setApellidos(actualizarUsuario.getApellidos());
            usuario.setCorreo_electronico(actualizarUsuario.getCorreo_electronico());
            usuario.setContrasena(actualizarUsuario.getContrasena());
            usuario.setDireccion(actualizarUsuario.getDireccion());
            usuario.setNumero_telefonico(actualizarUsuario.getNumero_telefonico());
            usuario.setPedidos(actualizarUsuario.getPedidos());

            return usuarioRepository.save(usuario);
        }

        return null;
    }

    public void borrarUsuario (Long id){
        usuarioRepository.deleteById(id);
    }

    public Usuario login(String correo){
        Usuario user = usuarioRepository.findByCorreoElectronico(correo).get();
        return user;
    }
}
