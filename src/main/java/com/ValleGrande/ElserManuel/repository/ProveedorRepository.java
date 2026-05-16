package com.ValleGrande.ElserManuel.repository;

import com.ValleGrande.ElserManuel.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    List<Proveedor> findByEstadoTrue();
}