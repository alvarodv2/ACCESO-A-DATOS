package com.controlador.controllers;

import com.controlador.entity.Clientes;
import com.controlador.entity.CuentaBancaria;
import com.controlador.repository.ClientesRepositorio;
import com.controlador.repository.CuentaBancariaRepositorio;
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
@RequestMapping("/cuentas_bancarias")
public class CuentaBancariaController {

    @Autowired
    private CuentaBancariaRepositorio cuentaBancariaRepositorio;

    @Autowired
    private ClientesRepositorio clienteRepositorio;

    @PersistenceContext
    private EntityManager entityManager;

    // Obtener todas las cuentas
    @GetMapping
    public List<CuentaBancaria> getAllCuentas() {
        return cuentaBancariaRepositorio.findAll();
    }

    // Obtener cuenta por su ID
    @GetMapping("/{id}")
    public Optional<CuentaBancaria> getCuentaById(@PathVariable Long id) {
        return cuentaBancariaRepositorio.findById(id);
    }

    // Crear una nueva cuenta bancaria
    @PostMapping
    public CuentaBancaria createCuenta(@RequestBody CuentaBancaria cuentaBancaria) {
        Optional<Clientes> clienteOpt = clienteRepositorio.findById(cuentaBancaria.getCliente().getIdCliente());

        if (clienteOpt.isPresent()) {
            Clientes cliente = clienteOpt.get();
            cuentaBancaria.setCliente(cliente);
            return cuentaBancariaRepositorio.save(cuentaBancaria);
        } else {
            throw new RuntimeException("Cliente no encontrado con ID: " + cuentaBancaria.getCliente().getIdCliente());
        }
    }

    // Actualizar cuenta bancaria existente
    @PutMapping("/{id}")
    public CuentaBancaria updateCuenta(@PathVariable Long id, @RequestBody CuentaBancaria cuentaBancaria) {
        return cuentaBancariaRepositorio.findById(id)
                .map(c -> {
                    c.setNumeroCuenta(cuentaBancaria.getNumeroCuenta());
                    c.setSaldo(cuentaBancaria.getSaldo());

                    Optional<Clientes> clienteOpt = clienteRepositorio.findById(cuentaBancaria.getCliente().getIdCliente());
                    if (clienteOpt.isPresent()) {
                        c.setCliente(clienteOpt.get());
                    } else {
                        throw new RuntimeException("Cliente no encontrado");
                    }

                    return cuentaBancariaRepositorio.save(c);
                }).orElseGet(() -> {
                    cuentaBancaria.setIdCuenta(id);
                    return cuentaBancariaRepositorio.save(cuentaBancaria);
                });
    }

    // Eliminar cuenta
    @DeleteMapping("/{id}")
    public void deleteCuenta(@PathVariable Long id) {
        cuentaBancariaRepositorio.deleteById(id);
    }

    // Buscar cuentas con filtros dinámicos
    @GetMapping("/buscar")
    public List<CuentaBancaria> buscarCuentas(@RequestParam(required = false) String numeroCuenta,
                                              @RequestParam(required = false) Double saldoMinimo,
                                              @RequestParam(required = false) Double saldoMaximo,
                                              @RequestParam(required = false) String orderBy,
                                              @RequestParam(defaultValue = "false") boolean all,
                                              @RequestParam(defaultValue = "10") int maxResults,
                                              @RequestParam(defaultValue = "0") int firstResult) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CuentaBancaria> cq = cb.createQuery(CuentaBancaria.class);
        Root<CuentaBancaria> root = cq.from(CuentaBancaria.class);

        List<Predicate> predicates = new ArrayList<>();

        // Filtros dinámicos
        if (numeroCuenta != null && !numeroCuenta.isEmpty()) {
            predicates.add(cb.like(root.get("numeroCuenta"), "%" + numeroCuenta + "%"));
        }
        if (saldoMinimo != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("saldo"), saldoMinimo));
        }
        if (saldoMaximo != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("saldo"), saldoMaximo));
        }

        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        // Ordenamiento
        if (orderBy != null && !orderBy.isEmpty()) {
            cq.orderBy(cb.asc(root.get(orderBy)));
        }

        TypedQuery<CuentaBancaria> query = entityManager.createQuery(cq);
        if (!all) {
            query.setMaxResults(maxResults);
            query.setFirstResult(firstResult);
        }
        return query.getResultList();
    }
}
