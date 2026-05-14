package com.turings.backend.service;

import com.turings.backend.model.Categoria;
import com.turings.backend.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repoCategorias;

    public CategoriaService(CategoriaRepository repoCategorias) {
        this.repoCategorias = repoCategorias;
    }

    // Obtener categoría
    public List<Categoria> obtenerCategorias(){

        return repoCategorias.findAll();
    }

    // Crear categoría
    public Categoria crearCategoria(Categoria categoria){

        return repoCategorias.save(categoria);
    }

    // Actualizar categoría
    public Categoria guardarCategoria(Long id, Categoria nuevaCategoria){

        Categoria categoriaExistente = repoCategorias.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Categoria no encontrada"));

        categoriaExistente.setCategoria(
                nuevaCategoria.getCategoria()
        );

        return repoCategorias.save(categoriaExistente);
    }

    // Eliminar categoría
    public void eliminarCategoria(Long id){

        repoCategorias.deleteById(id);
    }
}
