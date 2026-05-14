package com.turings.backend.controller;


import com.turings.backend.model.Producto;
import com.turings.backend.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> getAllProductos(){
        return productoService.getAllProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getUserById(@PathVariable Long id) {
        return productoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public Producto saveProducto(@RequestBody Producto producto){
        return productoService.saveProducto(producto);
    }

    @PutMapping("/{id}")
    public Producto updateProducto(@PathVariable Long id, @RequestBody Producto producto){
        return productoService.updateProducto(id,producto);
    }

    @DeleteMapping("/{id}")
    public void deleteProducto(@PathVariable Long id){
        productoService.deleteProduct(id);
    }



}
