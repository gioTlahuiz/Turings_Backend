package com.turings.backend.controller;


import com.turings.backend.dto.AuthResponse;
import com.turings.backend.dto.LoginRequest;
import com.turings.backend.model.Usuario;
import com.turings.backend.repository.UsuarioRepository;
import com.turings.backend.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public AuthController(AuthenticationManager authenticationManager,
                                 JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest) {
        /*
         * authenticationManager.authenticate(...) valida las credenciales.
         *
         * Si username/password son incorrectos, Spring lanza error 401.
         * Si son correctos, regresa un objeto Authentication autenticado.
         */
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token, "Bearer", jwtService.getExpirationTimeMs());
    }

    @PostMapping("/registro")
    public ResponseEntity<String> registro(@RequestBody Usuario usuario){
        if (usuarioRepository.findByCorreoElectronico(usuario.getCorreo_electronico()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: El usuario ya existe.");
        }
        System.out.println(usuario);
        usuario.setContrasena(passwordEncoder .encode(usuario.getContrasena()));
        // 5. Guardar en la base de datos
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuario registrado exitosamente");
    }
}
