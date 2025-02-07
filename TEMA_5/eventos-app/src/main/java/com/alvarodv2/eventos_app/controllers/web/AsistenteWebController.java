package com.alvarodv2.eventos_app.controllers.web;

import com.alvarodv2.eventos_app.model.entity.Asistente;
import com.alvarodv2.eventos_app.model.service.AsistenteService;
import com.alvarodv2.eventos_app.model.service.UsuarioService;
import com.alvarodv2.eventos_app.model.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/asistentes")
public class AsistenteWebController {

    @Autowired
    private AsistenteService asistenteService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public String listarAsistentes(Model model) {
        model.addAttribute("asistentes", asistenteService.findAll());
        return "asistentes/listar";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoAsistente(Model model) {
        // Pasar un nuevo asistente vacío al formulario
        model.addAttribute("asistente", new Asistente());

        // Pasar la lista de usuarios y eventos disponibles
        model.addAttribute("usuarios", usuarioService.findAll());
        model.addAttribute("eventos", eventoService.findAll());

        return "asistentes/formulario";
    }

    @PostMapping
    public String guardarAsistente(@ModelAttribute Asistente asistente) {
        asistenteService.save(asistente);
        return "redirect:/asistentes";
    }
}