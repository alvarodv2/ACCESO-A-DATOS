package com.controlador.controllers;

import com.controlador.entity.Clientes;
import com.controlador.repository.ClientesRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClientesController {

    @Autowired
    private ClientesRepositorio clientesRepositorio;

    // Obtener todos los clientes
    @GetMapping
    public List<Clientes> getAllClientes() {
        return clientesRepositorio.findAll();
    }

    // Obtener un cliente por su ID
    @GetMapping("/{id}")
    public Optional<Clientes> getClienteById(@PathVariable Long id) {
        return clientesRepositorio.findById(id);
    }

    // Crear un nuevo cliente
    @PostMapping
    public Clientes createCliente(@RequestBody Clientes cliente) {
        return clientesRepositorio.save(cliente);
    }

    // Actualizar un cliente existente
    @PutMapping("/{id}")
    public Clientes updateCliente(@PathVariable Long id, @RequestBody Clientes cliente) {
        return clientesRepositorio.findById(id)
                .map(c -> {
                    c.setNombre(cliente.getNombre());
                    c.setApellido(cliente.getApellido());
                    c.setDireccion(cliente.getDireccion());
                    return clientesRepositorio.save(c);
                }).orElseGet(() -> {
                    cliente.setIdCliente(id);
                    return clientesRepositorio.save(cliente);
                });
    }

    // Eliminar un cliente
    @DeleteMapping("/{id}")
    public void deleteCliente(@PathVariable Long id) {
        clientesRepositorio.deleteById(id);
    }
}
