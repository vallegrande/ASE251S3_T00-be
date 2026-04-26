package com.ValleGrande.ElserManuel.repository;

import com.ValleGrande.ElserManuel.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    List<Empleado> findByEstadoTrue();
}
