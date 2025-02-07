package com.alvarodv2.eventos_app.model.repository;

import com.alvarodv2.eventos_app.model.entity.Asistente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AsistenteRepository extends JpaRepository<Asistente, Long> {
    List<Asistente> findByEventoId(Long eventoId);
    List<Asistente> findByUsuarioId(Long usuarioId);
}