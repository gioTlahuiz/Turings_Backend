package com.turings.backend.model;

import jakarta.persistence.*;

@Entity(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id_categoria;

    private String categoria;

    public Categoria() {}

    public Categoria(String categoria) {
        this.categoria = categoria;
    }

    public Long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
