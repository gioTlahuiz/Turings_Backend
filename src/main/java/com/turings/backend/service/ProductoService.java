package com.turings.backend.service;


import com.turings.backend.model.Categoria;
import com.turings.backend.model.DetallesPedidos;
import com.turings.backend.model.Producto;
import com.turings.backend.repository.CategoriaRepository;
import com.turings.backend.repository.DetallesPedidosRepository;
import com.turings.backend.repository.ProductoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final DetallesPedidosRepository detallesPedidosRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository, DetallesPedidosRepository detallesPedidosRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.detallesPedidosRepository = detallesPedidosRepository;
    }

    public ProductoRepository getProductoRepository() {
        return productoRepository;
    }

    public CategoriaRepository getCategoriaRepository() {
        return categoriaRepository;
    }

    public DetallesPedidosRepository getDetallesPedidosRepository() {
        return detallesPedidosRepository;
    }

    public List<Producto> getAllProductos(){
        return productoRepository.findAll();
    }



    public Producto saveProducto(Producto producto){
        return productoRepository.save(producto);
    }

    public Producto updateProducto(Long id, Producto productoAct){
        Producto producto = productoRepository.findById(id).orElse(null);

        if (producto != null){
            producto.setColor(productoAct.getColor());
            producto.setDescripcion(productoAct.getDescripcion());
            producto.setDescuento(productoAct.getDescuento());
            producto.setDiseno(productoAct.isDiseno());
            producto.setImagen(productoAct.getImagen());
            producto.setNombre(productoAct.getNombre());
            producto.setPrecio(productoAct.getPrecio());
            producto.setStock(productoAct.getStock());
            producto.setCategoria(productoAct.getCategoria());

            return productoRepository.save(producto);
        }
        return null;

    }

    public void deleteProduct(Long id){
        productoRepository.deleteById(id);
    }

    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

//    private void assignRelations(Producto producto) {
//        if (producto.getCategoria() != null && producto.getCategoria().getId_categoria() != null){
//            Categoria categoria = categoriaRepository.findById(producto.getCategoria().getId_categoria()).orElse(null);
//            producto.setCategoria(categoria);
//        }
//    }


}
