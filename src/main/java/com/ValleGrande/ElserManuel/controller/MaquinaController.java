package com.ValleGrande.ElserManuel.controller;

import com.ValleGrande.ElserManuel.entity.Maquina;
import com.ValleGrande.ElserManuel.service.MaquinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/maquinas")
public class MaquinaController {
    @Autowired private MaquinaService service;

    @GetMapping
    public List<Maquina> listar() { return service.listarActivos(); }

    @GetMapping("/{id}")
    public ResponseEntity<Maquina> obtener(@PathVariable Long id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Maquina guardar(@RequestBody Maquina m) { return service.guardar(m); }

    @PutMapping("/{id}")
    public ResponseEntity<Maquina> actualizar(@PathVariable Long id, @RequestBody Maquina m) {
        try {
            return ResponseEntity.ok(service.actualizar(id, m));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarLogicamente(id);
        return ResponseEntity.noContent().build();
    }
}
