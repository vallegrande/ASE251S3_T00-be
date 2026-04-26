package com.ValleGrande.ElserManuel.service;

import com.ValleGrande.ElserManuel.entity.Maquina;
import com.ValleGrande.ElserManuel.repository.MaquinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MaquinaService {
    @Autowired private MaquinaRepository repository;

    public List<Maquina> listarActivos() { return repository.findByEstadoTrue(); }
    public Optional<Maquina> buscarPorId(Long id) { return repository.findById(id).filter(Maquina::getEstado); }
    public Maquina guardar(Maquina m) { m.setEstado(true); return repository.save(m); }
    
    public Maquina actualizar(Long id, Maquina m) {
        return repository.findById(id).map(existente -> {
            existente.setNombre(m.getNombre());
            existente.setMarca(m.getMarca());
            existente.setModelo(m.getModelo());
            existente.setPrecio(m.getPrecio());
            existente.setCategoria(m.getCategoria());
            return repository.save(existente);
        }).orElseThrow(() -> new RuntimeException("Máquina no encontrada"));
    }

    public void eliminarLogicamente(Long id) {
        repository.findById(id).ifPresent(m -> {
            m.setEstado(false);
            repository.save(m);
        });
    }
}
