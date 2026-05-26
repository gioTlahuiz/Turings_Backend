package com.turings.backend.service;

import com.turings.backend.model.Usuario;
import com.turings.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreoElectronico(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        if (usuario.getRol().isEmpty()){
            usuario.setRol("USER");
        }
        return User.builder()
                .authorities(usuario.getRol())
                .username(usuario.getCorreo_electronico())
                .password(usuario.getContrasena())
                .roles(usuario.getRol())
                .build();
    }


}
