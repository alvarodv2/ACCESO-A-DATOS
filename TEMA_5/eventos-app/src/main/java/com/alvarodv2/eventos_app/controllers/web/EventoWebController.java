package com.alvarodv2.eventos_app.controllers.web;

import com.alvarodv2.eventos_app.model.entity.Evento;
import com.alvarodv2.eventos_app.model.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/eventos")
public class EventoWebController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public String listarEventos(Model model) {
        model.addAttribute("eventos", eventoService.findAll());
        return "eventos/listar";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoEvento(Model model) {
        model.addAttribute("evento", new Evento());
        return "eventos/formulario";
    }

    @PostMapping
    public String guardarEvento(@ModelAttribute Evento evento) {
        eventoService.save(evento);
        return "redirect:/eventos";
    }
}