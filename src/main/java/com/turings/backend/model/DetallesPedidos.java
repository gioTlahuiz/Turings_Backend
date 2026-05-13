package com.turings.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "detalles_pedidos") // Verificar que coincida el nombre con el de la DB
public class DetallesPedidos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id_detalle;
    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    private int cantidad_producto;
    private Double precio_total;
    private String rastreador;
    private String imagen;
    private String estado_pedido;

    @ManyToOne
//    @JoinColumn(name = "id_producto")
    @JoinColumn(name = "productos_id_producto") // Ahora coincide con tu DB
    private Producto producto;

    public DetallesPedidos() {
    }

    public DetallesPedidos(Long id_detalle, Pedido pedido, int cantidad_producto, Double precio_total, String rastreador, String imagen, String estado_pedido) {
        this.id_detalle = id_detalle;
        this.pedido = pedido;
        this.cantidad_producto = cantidad_producto;
        this.precio_total = precio_total;
        this.rastreador = rastreador;
        this.imagen = imagen;
        this.estado_pedido = estado_pedido;
    }

    public Long getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(Long id_detalle) {
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
