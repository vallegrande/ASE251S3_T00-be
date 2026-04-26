package com.ValleGrande.ElserManuel.repository;

import com.ValleGrande.ElserManuel.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByEstadoTrue();
}
