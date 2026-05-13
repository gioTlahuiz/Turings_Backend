package com.turings.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id_pedido;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    private Date fecha_pedido;
    private String direccion;
    private String rastreador;

    @OneToMany(mappedBy = "pedido")
    @JsonIgnore
    private List<DetallesPedidos> detallesPedidos;

    @OneToOne(mappedBy = "pedido")
    @JsonIgnore
    private Review review;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<DetallesPedidos> getDetallesPedidos() {
        return detallesPedidos;
    }

    public void setDetallesPedidos(List<DetallesPedidos> detallesPedidos) {
        this.detallesPedidos = detallesPedidos;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Pedido() {
    }

    public Pedido(int id_pedido, Date fecha_pedido, String direccion, String rastreador) {
        this.id_pedido = id_pedido;
        this.fecha_pedido = fecha_pedido;
        this.direccion = direccion;
        this.rastreador = rastreador;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Date getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(Date fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRastreador() {
        return rastreador;
    }

    public void setRastreador(String rastreador) {
        this.rastreador = rastreador;
    }
}
