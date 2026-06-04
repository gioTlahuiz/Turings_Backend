package com.turings.backend.controller;

import com.turings.backend.model.Pedido;
import com.turings.backend.model.Review;
import com.turings.backend.model.Usuario;
import com.turings.backend.repository.PedidoRepository;
import com.turings.backend.repository.UsuarioRepository;
import com.turings.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PedidoRepository pedidoRepository;


    @GetMapping
    public List<Review> getReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/user")
    public List<Review> getReviewsUser(@AuthenticationPrincipal UserDetails userDetails) {

        Usuario usuario = usuarioRepository.findByCorreoElectronico(userDetails.getUsername())
                .orElseThrow();

        return reviewService.getAllUserReviews(usuario.getId_usuario());
    }


    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }


    @PostMapping
    public Review createReview(@RequestBody Review review,@AuthenticationPrincipal UserDetails userDetails) {

        Usuario usuario = usuarioRepository.findByCorreoElectronico(userDetails.getUsername())
                .orElseThrow();

        Pedido pedido = pedidoRepository.findById(review.getPedido().getId_pedido()).
                orElseThrow();

        if (pedido.getUsuario().getId_usuario() != usuario.getId_usuario()){
            throw new AuthorizationDeniedException("Authorization denied");
        }

        return reviewService.saveReview(review);
    }


    @PutMapping("/{id}")
    public Review updateReview(@PathVariable Long id, @RequestBody Review reviewDetails) {
        return reviewService.updateReview(id, reviewDetails);
    }


    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }
}