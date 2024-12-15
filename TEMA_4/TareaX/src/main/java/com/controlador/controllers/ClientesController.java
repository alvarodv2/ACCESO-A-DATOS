package com.controlador.controllers;

import com.controlador.entity.Clientes;
import com.controlador.repository.ClientesRepositorio;
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
@RequestMapping("/clientes")
public class ClientesController {

    @Autowired
    private ClientesRepositorio clientesRepositorio;

    @PersistenceContext
    private EntityManager entityManager;

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

    // Buscar clientes con filtros dinámicos
    @GetMapping("/buscar")
    public List<Clientes> buscarClientes(@RequestParam(required = false) String nombre,
                                         @RequestParam(required = false) String apellido,
                                         @RequestParam(required = false) String direccion,
                                         @RequestParam(required = false) String orderBy,
                                         @RequestParam(defaultValue = "false") boolean all,
                                         @RequestParam(defaultValue = "10") int maxResults,
                                         @RequestParam(defaultValue = "0") int firstResult) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Clientes> cq = cb.createQuery(Clientes.class);
        Root<Clientes> root = cq.from(Clientes.class);

        List<Predicate> predicates = new ArrayList<>();

        // Filtros dinámicos
        if (nombre != null && !nombre.isEmpty()) {
            predicates.add(cb.like(root.get("nombre"), "%" + nombre + "%"));
        }
        if (apellido != null && !apellido.isEmpty()) {
            predicates.add(cb.like(root.get("apellido"), "%" + apellido + "%"));
        }
        if (direccion != null && !direccion.isEmpty()) {
            predicates.add(cb.like(root.get("direccion"), "%" + direccion + "%"));
        }

        // Añadir los filtros
        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        // Ordenamiento si se pasa un parámetro orderBy
        if (orderBy != null && !orderBy.isEmpty()) {
            cq.orderBy(cb.asc(root.get(orderBy)));
        }

        // Crear la consulta
        TypedQuery<Clientes> query = entityManager.createQuery(cq);
        if (!all) {
            query.setMaxResults(maxResults);
            query.setFirstResult(firstResult);
        }
        return query.getResultList();
    }
}
