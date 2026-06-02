package com.turings.backend.DTO;

import com.turings.backend.model.Producto;
import org.springframework.web.multipart.MultipartFile;

public class UploadProductoRequest extends Producto {
    private MultipartFile imagenFile;

    public MultipartFile getImagenFile() {
        return imagenFile;
    }

    public void setImagenFile(MultipartFile imagen) {
        this.imagenFile = imagen;
    }
}
