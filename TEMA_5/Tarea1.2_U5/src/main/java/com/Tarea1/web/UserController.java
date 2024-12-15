package com.Tarea1.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * El controlador se encarga de manejar las peticiones HTTP y devolver los datos
 * que se mostrarán en la plantilla HTML. En este caso, la ruta '/pruebas' se
 * manejará con este controlador.
 */
@Controller
public class UserController {

    /**
     * Este metodo maneja las solicitudes GET en "/pruebas".
     * Se encarga de pasar datos a la plantilla HTML y devolver la vista correspondiente.
     *
     * @param model El modelo que se usa para enviar datos a la vista
     * @return El nombre de la plantilla que se mostrará en el navegador
     */
    @GetMapping("/pruebas")
    public String pruebas(Model model) {
        // Agregamos los atributos al modelo, que serán utilizados en la plantilla HTML
        model.addAttribute("title", "Título");
        model.addAttribute("nombre", "Pepe");
        model.addAttribute("apellido", "Gomez");

        // El nombre de la plantilla (HTML) que se usará para renderizar la respuesta
        return "pruebas";
    }
}
