package com.turings.backend.controller;


import com.turings.backend.DTO.AuthResponse;
import com.turings.backend.DTO.LoginRequest;
import com.turings.backend.model.Usuario;
import com.turings.backend.repository.UsuarioRepository;
import com.turings.backend.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
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

    @GetMapping
    public Usuario loginActual(@AuthenticationPrincipal UserDetails userDetails){
        Usuario usuario = usuarioRepository.findByCorreoElectronico(userDetails.getUsername()).get();
        return usuario;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest) {
        /*
         * authenticationManager.authenticate(...) valida las credenciales.
         *
         * Si username/password son incorrectos, Spring lanza error 401.
         * Si son correctos, regresa un objeto Authentication autenticado.
         */
        System.out.println(loginRequest.getPassword());
        System.out.println(loginRequest.getUsername());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        System.out.println(loginRequest);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println(userDetails);
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token, "Bearer", jwtService.getExpirationTimeMs(),userDetails.getAuthorities().toString());
    }

    @PostMapping("/registro")
    public ResponseEntity<String> registro(@RequestBody Usuario usuario){
        usuario.setRol("USER");
        if (usuarioRepository.findByCorreoElectronico(usuario.getCorreo_electronico()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: El usuario ya existe.");
        }
        System.out.println(usuario);
        usuario.setRol("USER");
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        // 5. Guardar en la base de datos
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Usuario registrado exitosamente");
    }
}
