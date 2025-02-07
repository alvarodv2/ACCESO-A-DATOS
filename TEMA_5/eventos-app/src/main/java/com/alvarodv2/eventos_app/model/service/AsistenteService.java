package com.alvarodv2.eventos_app.model.service;

import com.alvarodv2.eventos_app.model.entity.Asistente;
import com.alvarodv2.eventos_app.model.repository.AsistenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AsistenteService {

    @Autowired
    private AsistenteRepository asistenteRepository;

    public List<Asistente> findAll() {
        return asistenteRepository.findAll();
    }

    public Optional<Asistente> findById(Long id) {
        return asistenteRepository.findById(id);
    }

    public Asistente save(Asistente asistente) {
        return asistenteRepository.save(asistente);
    }

    public void deleteById(Long id) {
        asistenteRepository.deleteById(id);
    }

    public List<Asistente> findByEventoId(Long eventoId) {
        return asistenteRepository.findByEventoId(eventoId);
    }

    public List<Asistente> findByUsuarioId(Long usuarioId) {
        return asistenteRepository.findByUsuarioId(usuarioId);
    }
}