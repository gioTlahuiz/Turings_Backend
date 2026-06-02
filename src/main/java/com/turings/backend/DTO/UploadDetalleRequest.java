package com.turings.backend.DTO;


import com.turings.backend.model.DetallesPedidos;
import org.springframework.web.multipart.MultipartFile;

public class UploadDetalleRequest extends DetallesPedidos {
    private MultipartFile imagenFile;

    public MultipartFile getImagenFile() {
        return imagenFile;
    }

    public void setImagenFile(MultipartFile imagen) {
        this.imagenFile = imagen;
    }
}
