package com.controlador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.controlador.entity.Clientes;

@Repository
public interface ClientesRepositorio extends JpaRepository<Clientes, Long> {

}
