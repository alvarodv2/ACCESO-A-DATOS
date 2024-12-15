package com.Tarea1.web;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class ProductController {

    @GetMapping("users")
    public Map<String,Object> pruebas() {
        // JSON
        Map<String,Object> body = new HashMap<>();
        body.put("title", "titulo hola mundo");
        body.put("nombre", "nombre");
        body.put("apellidos", "apellidos");

        return body;
    }

    @PostMapping("create")
    public String postMethodName(@RequestBody String entity) {
        try {
            // Crear un archivo temporal como ejemplo
            File.createTempFile("asda", "adsasd");
        } catch (Exception ex) {
        }
        // Reemplazar "Gomez" por "Gómez"
        return entity.replace("Gomez", "Gómez");
    }
}
