package com.ValleGrande.ElserManuel.service;

import com.ValleGrande.ElserManuel.entity.Categoria;
import com.ValleGrande.ElserManuel.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired private CategoriaRepository repository;

    public List<Categoria> listarActivos() { return repository.findByEstadoTrue(); }
    public Optional<Categoria> buscarPorId(Long id) { return repository.findById(id).filter(Categoria::getEstado); }
    public Categoria guardar(Categoria c) { c.setEstado(true); return repository.save(c); }
    public void eliminarLongicamente(Long id) {
        repository.findById(id).ifPresent(c -> {
            c.setEstado(false);
            repository.save(c);
        });
    }
}
