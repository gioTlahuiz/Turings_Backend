package com.turings.backend.model;

import jakarta.persistence.*;

import java.util.Date;

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
