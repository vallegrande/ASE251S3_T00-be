package com.ValleGrande.ElserManuel.controller;

import com.ValleGrande.ElserManuel.entity.Categoria;
import com.ValleGrande.ElserManuel.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {
    @Autowired private CategoriaService service;

    @GetMapping
    public List<Categoria> listar() { return service.listarActivos(); }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtener(@PathVariable Long id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Categoria guardar(@RequestBody Categoria c) { return service.guardar(c); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarLongicamente(id);
        return ResponseEntity.noContent().build();
    }
}
