package com.turings.backend.controller;

import com.turings.backend.model.Categoria;
import com.turings.backend.service.CategoriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    private final CategoriaService servCategoria;

    public CategoriaController(CategoriaService servCategoria) {
        this.servCategoria = servCategoria;
    }

    @GetMapping
    public List<Categoria> obtenerCategorias() {

        return servCategoria.obtenerCategorias();
    }

    @PostMapping
    public Categoria crearCategoria(@RequestBody Categoria categoria) {

        return servCategoria.crearCategoria(categoria);
    }

    @PutMapping("/{id}")
    public Categoria actualizarCategoria(
            @PathVariable Long id,
            @RequestBody Categoria categoria) {

        return servCategoria.guardarCategoria(id, categoria);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {

        servCategoria.eliminarCategoria(id);
    }
}
