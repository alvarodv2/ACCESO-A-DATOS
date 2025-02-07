package com.alvarodv2.eventos_app.controllers.api;

import com.alvarodv2.eventos_app.model.entity.Evento;
import com.alvarodv2.eventos_app.model.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/eventos")
public class EventoApiController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public List<Evento> listarEventos() {
        return eventoService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Evento> obtenerEvento(@PathVariable Long id) {
        return eventoService.findById(id);
    }

    @PostMapping
    public Evento crearEvento(@RequestBody Evento evento) {
        return eventoService.save(evento);
    }

    @DeleteMapping("/{id}")
    public void eliminarEvento(@PathVariable Long id) {
        eventoService.deleteById(id);
    }
}