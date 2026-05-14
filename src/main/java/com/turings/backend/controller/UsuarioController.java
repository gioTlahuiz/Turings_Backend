package com.turings.backend.controller;

import com.turings.backend.model.Usuario;
import com.turings.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/usuario")
@CrossOrigin(origins = "*")

public class UsuarioController {
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController (UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> getAllUsuarios(){
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public Usuario getUsuario(@PathVariable("id")Long id){
        return usuarioService.getUsuarioById(id);
    }

    @PostMapping
    public Usuario guardarUsuario (@RequestBody Usuario usuario){
        return usuarioService.guardarUsuario(usuario);
    }

    @PutMapping("/{id}")
    public Usuario actualizarUsuario (@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.actualizarUsuario(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void borrarUsuario (@PathVariable Long id) {
        usuarioService.borrarUsuario(id);
    }
}
