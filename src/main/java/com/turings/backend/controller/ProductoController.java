package com.turings.backend.controller;


import com.turings.backend.DTO.UploadProductoRequest;
import com.turings.backend.model.Producto;
import com.turings.backend.service.FileStorageService;
import com.turings.backend.service.ProductoService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/productos")
@CrossOrigin(origins="*")
public class ProductoController {


    private final FileStorageService storageService;
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService, FileStorageService storageService) {
        this.productoService = productoService;
        this.storageService = storageService;
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
    public Producto saveProducto(@ModelAttribute UploadProductoRequest parametro){
        parametro.setImagen( "/api/v1/productos/img/" + storageService.saveProduct(parametro.getImagenFile()));

        Producto producto = new Producto();

        producto.setColor(parametro.getColor());
        producto.setDescripcion(parametro.getDescripcion());
        producto.setDescuento(parametro.getDescuento());
        producto.setDiseno(parametro.isDiseno());
        producto.setImagen(parametro.getImagen()); // El string con la ruta de la imagen
        producto.setNombre(parametro.getNombre());
        producto.setPrecio(parametro.getPrecio());
        producto.setStock(parametro.getStock());
        producto.setTalla(parametro.getTalla());
        producto.setCategoria(parametro.getCategoria());

        return productoService.saveProducto(producto);
    }

    @PutMapping("/{id}")
    public Producto updateProducto(@PathVariable Long id, @ModelAttribute UploadProductoRequest parametro){
        parametro.setImagen( "/api/v1/productos/img/" + storageService.saveProduct(parametro.getImagenFile()));

        Producto producto = new Producto();

        producto.setColor(parametro.getColor());
        producto.setDescripcion(parametro.getDescripcion());
        producto.setDescuento(parametro.getDescuento());
        producto.setDiseno(parametro.isDiseno());
        producto.setImagen(parametro.getImagen()); // El string con la ruta de la imagen
        producto.setNombre(parametro.getNombre());
        producto.setPrecio(parametro.getPrecio());
        producto.setStock(parametro.getStock());
        producto.setTalla(parametro.getTalla());
        producto.setCategoria(parametro.getCategoria());


        return productoService.updateProducto(id,producto);
    }

    @DeleteMapping("/{id}")
    public void deleteProducto(@PathVariable Long id){
        productoService.deleteProduct(id);
    }


    @GetMapping("/img/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        System.out.println(filename);
        Resource file = storageService.getProductoImg(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}
