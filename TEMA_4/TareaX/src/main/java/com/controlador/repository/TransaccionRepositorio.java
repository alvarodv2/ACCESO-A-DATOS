package com.controlador.repository;

import com.controlador.entity.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionRepositorio extends JpaRepository<Transaccion, Long> {

    // Consultar transacciones entre dos fechas
    @Query("SELECT t FROM Transaccion t WHERE t.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Transaccion> findTransaccionesEntreFechas(String fechaInicio, String fechaFin);

    // Eliminar transacciones que superen una cantidad
    @Modifying
    @Query("DELETE FROM Transaccion t WHERE t.monto > :monto")
    void deleteTransaccionesPorMontoMayorA(Double monto);
}
