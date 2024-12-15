package com.Tarea1.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @GetMapping("/pruebas")
    public String pruebas(Model model) {
        model.addAttribute("title", "Título");
        model.addAttribute("nombre", "Pepe");
        model.addAttribute("apellido", "Gomez");
        return "pruebas";  // Usará la plantilla 'pruebas.html'
    }

    @GetMapping("/contacto")
    public String contacto(Model model) {
        model.addAttribute("mensaje", "Hola desde Spring MVC");
        return "contacto";  // Usará la plantilla 'contacto.html'
    }

    @GetMapping("/productos")
    public String mostrarProductos(Model model) {
        // Creamos algunos productos de ejemplo para la práctica
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto(1, "Ordenador", 200.00));
        productos.add(new Producto(2, "Teclado", 25.50));
        productos.add(new Producto(3, "Ratón", 15.75));

        // Pasamos la lista de productos al modelo
        model.addAttribute("productos", productos);

        return "productos";  // Usará la plantilla 'productos.html'
    }
}
