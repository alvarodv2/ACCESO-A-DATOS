package com.alvarodv2.eventos_app.model.service;

import com.alvarodv2.eventos_app.model.entity.Evento;
import com.alvarodv2.eventos_app.model.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public List<Evento> findAll() {
        return eventoRepository.findAll();
    }

    public Optional<Evento> findById(Long id) {
        return eventoRepository.findById(id);
    }

    public Evento save(Evento evento) {
        return eventoRepository.save(evento);
    }

    public void deleteById(Long id) {
        eventoRepository.deleteById(id);
    }

    public List<Evento> findByFecha(LocalDate fecha) {
        return eventoRepository.findByFecha(fecha);
    }

    public List<Evento> buscarPorTitulo(String titulo) {
        return eventoRepository.buscarPorTitulo(titulo);
    }
}