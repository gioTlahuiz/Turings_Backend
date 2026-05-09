package com.turings.backend.model;

import jakarta.persistence.*;

import java.awt.*;
import java.util.Date;
@Entity(name = "detalle_pedido")
public class DetallesPedidos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id_detalle;
    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    private int cantidad_producto;
    private Double precio_total;
    private String rastreador;
    private String imagen;
    private String estado_pedido;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    public DetallesPedidos() {
    }

    public DetallesPedidos(int id_detalle, Pedido pedido, int cantidad_producto, Double precio_total, String rastreador, String imagen, String estado_pedido) {
        this.id_detalle = id_detalle;
        this.pedido = pedido;
        this.cantidad_producto = cantidad_producto;
        this.precio_total = precio_total;
        this.rastreador = rastreador;
        this.imagen = imagen;
        this.estado_pedido = estado_pedido;
    }

    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public int getCantidad_producto() {
        return cantidad_producto;
    }

    public void setCantidad_producto(int cantidad_producto) {
        this.cantidad_producto = cantidad_producto;
    }

    public Double getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(Double precio_total) {
        this.precio_total = precio_total;
    }

    public String getRastreador() {
        return rastreador;
    }

    public void setRastreador(String rastreador) {
        this.rastreador = rastreador;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getEstado_pedido() {
        return estado_pedido;
    }

    public void setEstado_pedido(String estado_pedido) {
        this.estado_pedido = estado_pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
