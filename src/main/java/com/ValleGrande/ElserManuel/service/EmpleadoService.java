package com.ValleGrande.ElserManuel.service;

import com.ValleGrande.ElserManuel.entity.Empleado;
import com.ValleGrande.ElserManuel.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository repository;

    public List<Empleado> listarActivos() {
        return repository.findByEstadoTrue();
    }

    public Optional<Empleado> buscarPorId(Long id) {
        return repository.findById(id).filter(Empleado::getEstado);
    }

    public Empleado guardar(Empleado e) {
        e.setEstado(true);
        return repository.save(e);
    }

    public Empleado actualizar(Long id, Empleado datos) {
        return repository.findById(id).map(existente -> {
            existente.setNombre(datos.getNombre());
            existente.setApellido(datos.getApellido());
            existente.setCargo(datos.getCargo());
            existente.setSueldo(datos.getSueldo());
            existente.setDni(datos.getDni());
            existente.setFechaIngreso(datos.getFechaIngreso());
            return repository.save(existente);
        }).orElseThrow(() -> new RuntimeException("Empleado no encontrado con id: " + id));
    }

    public void eliminarLogicamente(Long id) {
        repository.findById(id).ifPresent(e -> {
            e.setEstado(false);
            repository.save(e);
        });
    }
}