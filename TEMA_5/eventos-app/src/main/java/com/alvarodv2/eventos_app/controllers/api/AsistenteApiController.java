package com.alvarodv2.eventos_app.controllers.api;

import com.alvarodv2.eventos_app.model.entity.Asistente;
import com.alvarodv2.eventos_app.model.service.AsistenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/asistentes")
public class AsistenteApiController {

    @Autowired
    private AsistenteService asistenteService;

    @GetMapping
    public List<Asistente> listarAsistentes() {
        return asistenteService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Asistente> obtenerAsistente(@PathVariable Long id) {
        return asistenteService.findById(id);
    }

    @PostMapping
    public Asistente crearAsistente(@RequestBody Asistente asistente) {
        return asistenteService.save(asistente);
    }

    @DeleteMapping("/{id}")
    public void eliminarAsistente(@PathVariable Long id) {
        asistenteService.deleteById(id);
    }
}