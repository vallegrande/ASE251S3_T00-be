package com.ValleGrande.ElserManuel.service;

import com.ValleGrande.ElserManuel.entity.Proveedor;
import com.ValleGrande.ElserManuel.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository repository;

    public List<Proveedor> listarActivos() {
        return repository.findByEstadoTrue();
    }

    public Optional<Proveedor> buscarPorId(Long id) {
        return repository.findById(id).filter(Proveedor::getEstado);
    }

    public Proveedor guardar(Proveedor p) {
        p.setEstado(true);
        return repository.save(p);
    }

    public Proveedor actualizar(Long id, Proveedor datos) {
        return repository.findById(id).map(existente -> {
            existente.setRazonSocial(datos.getRazonSocial());
            existente.setRuc(datos.getRuc());
            existente.setTelefono(datos.getTelefono());
            existente.setCorreo(datos.getCorreo());
            existente.setDireccion(datos.getDireccion());
            existente.setCreditoMaximo(datos.getCreditoMaximo());
            return repository.save(existente);
        }).orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id: " + id));
    }

    public void eliminarLogicamente(Long id) {
        repository.findById(id).ifPresent(p -> {
            p.setEstado(false);
            repository.save(p);
        });
    }
}

