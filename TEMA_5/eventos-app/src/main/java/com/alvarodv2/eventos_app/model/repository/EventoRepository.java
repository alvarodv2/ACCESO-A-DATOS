package com.alvarodv2.eventos_app.model.repository;

import com.alvarodv2.eventos_app.model.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByFecha(LocalDate fecha);

    @Query("SELECT e FROM Evento e WHERE e.titulo LIKE %:titulo%")
    List<Evento> buscarPorTitulo(@Param("titulo") String titulo);
}