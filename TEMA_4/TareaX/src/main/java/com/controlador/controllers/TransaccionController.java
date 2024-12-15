package com.controlador.controllers;

import com.controlador.entity.Transaccion;
import com.controlador.repository.TransaccionRepositorio;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionRepositorio transaccionRepositorio;

    @PersistenceContext
    private EntityManager entityManager; // Inyección del EntityManager

    // Obtener todas las transacciones
    @GetMapping
    public List<Transaccion> getAllTransacciones() {
        return transaccionRepositorio.findAll();
    }

    // Obtener transacción por su ID
    @GetMapping("/{id}")
    public Optional<Transaccion> getTransaccionById(@PathVariable Long id) {
        return transaccionRepositorio.findById(id);
    }

    // Crear una nueva transacción
    @PostMapping
    public Transaccion createTransaccion(@RequestBody Transaccion transaccion) {
        return transaccionRepositorio.save(transaccion);
    }

    // Actualizar transacción existente
    @PutMapping("/{id}")
    public Transaccion updateTransaccion(@PathVariable Long id, @RequestBody Transaccion transaccion) {
        return transaccionRepositorio.findById(id)
                .map(t -> {
                    t.setCuentaOrigen(transaccion.getCuentaOrigen());
                    t.setCuentaDestino(transaccion.getCuentaDestino());
                    t.setMonto(transaccion.getMonto());
                    t.setFecha(transaccion.getFecha());
                    return transaccionRepositorio.save(t);
                }).orElseGet(() -> {
                    transaccion.setIdTransaccion(id);
                    return transaccionRepositorio.save(transaccion);
                });
    }

    // Eliminar transacción
    @DeleteMapping("/{id}")
    public void deleteTransaccion(@PathVariable Long id) {
        transaccionRepositorio.deleteById(id);
    }

    // Buscar transacciones con filtros dinámicos
    @GetMapping("/buscar")
    public List<Transaccion> buscarTransacciones(@RequestParam(required = false) Double montoMinimo,
                                                 @RequestParam(required = false) Double montoMaximo,
                                                 @RequestParam(required = false) String fechaInicio,
                                                 @RequestParam(required = false) String fechaFin,
                                                 @RequestParam(defaultValue = "false") boolean all,
                                                 @RequestParam(defaultValue = "10") int maxResults,
                                                 @RequestParam(defaultValue = "0") int firstResult) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaccion> cq = cb.createQuery(Transaccion.class);
        Root<Transaccion> root = cq.from(Transaccion.class);

        List<Predicate> predicates = new ArrayList<>();

        // Filtros dinámicos
        if (montoMinimo != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("monto"), montoMinimo));
        }
        if (montoMaximo != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("monto"), montoMaximo));
        }
        if (fechaInicio != null && fechaFin != null) {
            predicates.add(cb.between(root.get("fecha"), fechaInicio, fechaFin));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        TypedQuery<Transaccion> query = entityManager.createQuery(cq);
        if (!all) {
            query.setMaxResults(maxResults);
            query.setFirstResult(firstResult);
        }
        return query.getResultList();
    }
}
