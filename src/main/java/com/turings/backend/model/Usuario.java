package com.turings.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_usuario;
    private String nombre;
    private String apellidos;
    private String rol;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    private String direccion;
    private String numero_telefonico;
    private String contrasena;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Pedido> pedidos;

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Usuario(int id_usuario, String nombre, String apellidos, String rol, String correoElectronico, String direccion, String numero_telefonico, String contrasena) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.rol = rol;
        this.correoElectronico = correoElectronico;
        this.direccion = direccion;
        this.numero_telefonico = numero_telefonico;
        this.contrasena = contrasena;
    }

    public Usuario() {
    }

    public Usuario(String nombre, String apellidos, String correoElectronico, String direccion, String numero_telefonico, String contrasena) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correoElectronico = correoElectronico;
        this.direccion = direccion;
        this.numero_telefonico = numero_telefonico;
        this.contrasena = contrasena;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo_electronico() {
        return correoElectronico;
    }

    public void setCorreo_electronico(String correo_electronico) {
        this.correoElectronico = correo_electronico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumero_telefonico() {
        return numero_telefonico;
    }

    public void setNumero_telefonico(String numero_telefonico) {
        this.numero_telefonico = numero_telefonico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }



}
