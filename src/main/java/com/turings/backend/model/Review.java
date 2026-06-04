package com.turings.backend.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "reviews")

public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reviews")
    private int idReviews;

    private double calificacion;

    private String comentario;

    private Date fecha_review;

    @OneToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    public Review() {
    }

    public Review(int id_reviews, double calificacion, String comentario, Date fecha_review) {
        this.idReviews = id_reviews;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.fecha_review = fecha_review;
    }

    public int getId_reviews() {
        return idReviews;
    }

    public void setId_reviews(int id_reviews) {
        this.idReviews = id_reviews;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFecha_review() {
        return fecha_review;
    }

    public void setFecha_review(Date fecha_review) {
        this.fecha_review = fecha_review;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    }
