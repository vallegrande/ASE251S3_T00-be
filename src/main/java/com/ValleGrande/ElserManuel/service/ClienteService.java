package com.ValleGrande.ElserManuel.service;

import com.ValleGrande.ElserManuel.entity.Cliente;
import com.ValleGrande.ElserManuel.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired private ClienteRepository repository;

    public List<Cliente> listarActivos() { return repository.findByEstadoTrue(); }
    public Optional<Cliente> buscarPorId(Long id) { return repository.findById(id).filter(Cliente::getEstado); }
    public Cliente guardar(Cliente c) { c.setEstado(true); return repository.save(c); }
    public void eliminarLongicamente(Long id) {
        repository.findById(id).ifPresent(c -> {
            c.setEstado(false);
            repository.save(c);
        });
    }
}
