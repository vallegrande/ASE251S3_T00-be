package com.ValleGrande.ElserManuel.controller;

import com.ValleGrande.ElserManuel.entity.Cliente;
import com.ValleGrande.ElserManuel.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {
    @Autowired private ClienteService service;

    @GetMapping
    public List<Cliente> listar() { return service.listarActivos(); }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtener(@PathVariable Long id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cliente guardar(@RequestBody Cliente c) { return service.guardar(c); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarLongicamente(id);
        return ResponseEntity.noContent().build();
    }
}
