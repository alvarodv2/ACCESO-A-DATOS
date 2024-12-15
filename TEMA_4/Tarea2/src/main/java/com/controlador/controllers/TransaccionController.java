package com.controlador.controllers;

import com.controlador.entity.Transaccion;
import com.controlador.repository.TransaccionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionRepositorio transaccionRepositorio;

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
}
