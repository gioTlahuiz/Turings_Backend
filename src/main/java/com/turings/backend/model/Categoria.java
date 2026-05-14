package com.turings.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turings.backend.model.Usuario;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_categoria;

    private String categoria;

    public Categoria() {}

    public Categoria(String categoria) {
        this.categoria = categoria;
    }

    public Categoria(int id) {
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