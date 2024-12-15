package com.controlador.repository;

import com.controlador.entity.CuentaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaBancariaRepositorio extends JpaRepository<CuentaBancaria, Long> {

    // Query de cuentas con saldo > a un monto especificado.
    @Query("SELECT c FROM CuentaBancaria c WHERE c.saldo > :monto")
    List<CuentaBancaria> findCuentasConSaldoMayorQue(Double monto);
}
