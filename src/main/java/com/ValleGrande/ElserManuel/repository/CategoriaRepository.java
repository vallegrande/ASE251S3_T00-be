package com.ValleGrande.ElserManuel.repository;

import com.ValleGrande.ElserManuel.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByEstadoTrue();
}
