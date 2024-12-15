package com.controlador.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cuentas_bancarias")
public class CuentaBancaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuenta;

    @Column(name = "numero_cuenta", unique = true, nullable = false)
    private String numeroCuenta;

    @Column(name = "saldo", nullable = false)
    private Double saldo;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Clientes cliente;

    @JsonIgnore
    @OneToMany(mappedBy = "cuentaOrigen", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Transaccion> transaccionesOrigen = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cuentaDestino", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Transaccion> transaccionesDestino = new ArrayList<>();

    public CuentaBancaria() {}

    public CuentaBancaria(String numeroCuenta, Double saldo, Clientes cliente) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
        this.cliente = cliente;
    }

    // Getters y Setters (sin cambios)
    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public List<Transaccion> getTransaccionesOrigen() {
        return transaccionesOrigen;
    }

    public void setTransaccionesOrigen(List<Transaccion> transaccionesOrigen) {
        this.transaccionesOrigen = transaccionesOrigen;
    }

    public List<Transaccion> getTransaccionesDestino() {
        return transaccionesDestino;
    }

    public void setTransaccionesDestino(List<Transaccion> transaccionesDestino) {
        this.transaccionesDestino = transaccionesDestino;
    }
}