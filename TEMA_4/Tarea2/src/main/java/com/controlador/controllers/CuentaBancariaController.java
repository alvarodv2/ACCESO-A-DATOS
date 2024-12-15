package com.controlador.controllers;

import com.controlador.entity.CuentaBancaria;
import com.controlador.entity.Clientes;
import com.controlador.repository.CuentaBancariaRepositorio;
import com.controlador.repository.ClientesRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cuentas_bancarias")
public class CuentaBancariaController {

    @Autowired
    private CuentaBancariaRepositorio cuentaBancariaRepositorio;

    @Autowired
    private ClientesRepositorio clienteRepositorio; // Repositorio para consultar clientes

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
        // Buscar el cliente por su ID
        Optional<Clientes> clienteOpt = clienteRepositorio.findById(cuentaBancaria.getCliente().getIdCliente());

        // Verificar si el cliente existe
        if (clienteOpt.isPresent()) {
            Clientes cliente = clienteOpt.get();
            cuentaBancaria.setCliente(cliente); // Establecer el cliente en la cuenta bancaria
            return cuentaBancariaRepositorio.save(cuentaBancaria); // Guardar la cuenta bancaria
        } else {
            // Si no se encuentra el cliente, devolver un error
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

                    // Buscar y establecer el cliente
                    Optional<Clientes> clienteOpt = clienteRepositorio.findById(cuentaBancaria.getCliente().getIdCliente());
                    if (clienteOpt.isPresent()) {
                        c.setCliente(clienteOpt.get());
                    } else {
                        throw new RuntimeException("Cliente no encontrado");
                    }

                    return cuentaBancariaRepositorio.save(c);
                }).orElseGet(() -> {
                    // Si no existe, crear uno nuevo
                    cuentaBancaria.setIdCuenta(id);
                    return cuentaBancariaRepositorio.save(cuentaBancaria);
                });
    }

    // Eliminar cuenta
    @DeleteMapping("/{id}")
    public void deleteCuenta(@PathVariable Long id) {
        cuentaBancariaRepositorio.deleteById(id);
    }
}
