package com.turings.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_producto;
    private int cantidad;
    private String color;
    private String descripcion;
    private int descuento;
    private boolean diseno;
    private String imagen;
    private String nombre;
    private int precio;
    private int stock;
    private String talla;
    /* aqui van las llaves foraneas con las anotaciones*/

    public Producto(int id_Productos) {}

    public Producto(int id_Productos, int cantidad, String color, String descripcion, int descuento, boolean diseno, String imagen, String nombre, int precio, int stock, String talla) {
        this.id_producto = id_Productos;
        this.cantidad = cantidad;
        this.color = color;
        this.descripcion = descripcion;
        this.descuento = descuento;
        this.diseno = diseno;
        this.imagen = imagen;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.talla = talla;
    }

    public int getId_Productos() {
        return id_producto;
    }

    public void setId_Productos(int id_Productos) {
        this.id_producto = id_Productos;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public boolean isDiseno() {
        return diseno;
    }

    public void setDiseno(boolean diseno) {
        this.diseno = diseno;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }
}
