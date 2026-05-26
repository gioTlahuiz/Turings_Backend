package com.turings.backend.service;

import com.turings.backend.model.Review;
import com.turings.backend.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;


    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }


    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }


    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }


    public Review updateReview(Long id, Review reviewDetails) {
        return reviewRepository.findById(id).map(review -> {
            // Se usan los métodos setComentario y setCalificacion del modelo
            review.setComentario(reviewDetails.getComentario());
            review.setCalificacion(reviewDetails.getCalificacion());


            return reviewRepository.save(review);
        }).orElse(null);
    }



    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}