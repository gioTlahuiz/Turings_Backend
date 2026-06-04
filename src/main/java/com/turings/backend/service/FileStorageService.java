package com.turings.backend.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;

@Service
public class FileStorageService {

    private final Path root = Paths.get("Pictures/Producto");
    private final Path details = Paths.get("Pictures/Details");

    public String saveProduct(MultipartFile file) {
        try {
            if (!Files.exists(root)) {
                Files.createDirectories(root); // Esto creará tanto 'Pictures' como 'Producto'
            }
            String randomString = randomName();
            Files.copy(file.getInputStream(), this.root.resolve(randomString + file.getOriginalFilename()));
            return randomString + file.getOriginalFilename();
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public Resource getProductoImg(String filename){

        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public String saveDetails(MultipartFile file) {
        try {
            if (!Files.exists(details)) {
                Files.createDirectories(details); // Esto creará tanto 'Pictures' como 'Producto'
            }
            String randomString = randomName();
            Files.copy(file.getInputStream(), this.details.resolve(randomString + file.getOriginalFilename()));
            return randomString + file.getOriginalFilename();
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public Resource getDetailsImg(String filename){

        try {
            Path file = details.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    private String randomName(){
        StringBuilder salida = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            salida.append((int) (Math.random() * 10));
        }
        LocalDate date = LocalDate.now();
        salida.append( " - " + date  + " - ");

        System.out.println(salida);
        return salida.toString();
    }
}
