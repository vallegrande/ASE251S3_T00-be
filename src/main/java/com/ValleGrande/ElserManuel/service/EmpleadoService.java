package com.ValleGrande.ElserManuel.service;

import com.ValleGrande.ElserManuel.entity.Empleado;
import com.ValleGrande.ElserManuel.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {
    @Autowired private EmpleadoRepository repository;

    public List<Empleado> listarActivos() { return repository.findByEstadoTrue(); }
    public Optional<Empleado> buscarPorId(Long id) { return repository.findById(id).filter(Empleado::getEstado); }
    public Empleado guardar(Empleado e) { e.setEstado(true); return repository.save(e); }
    public void eliminarLongicamente(Long id) {
        repository.findById(id).ifPresent(e -> {
            e.setEstado(false);
            repository.save(e);
        });
    }
}
