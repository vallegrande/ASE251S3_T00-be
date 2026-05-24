package com.ValleGrande.ElserManuel.repository;

import com.ValleGrande.ElserManuel.entity.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {

    List<Alquiler> findByEstadoTrueOrderByIdDesc();

    Optional<Alquiler> findTopByOrderByIdDesc(); // para generar el siguiente número
}