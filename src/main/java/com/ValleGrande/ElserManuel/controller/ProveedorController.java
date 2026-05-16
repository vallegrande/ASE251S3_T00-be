package com.ValleGrande.ElserManuel.controller;


import com.ValleGrande.ElserManuel.entity.Proveedor;
import com.ValleGrande.ElserManuel.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService service;

    @GetMapping
    public List<Proveedor> listar() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtener(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Proveedor guardar(@RequestBody Proveedor p) {
        return service.guardar(p);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizar(@PathVariable Long id, @RequestBody Proveedor p) {
        try {
            return ResponseEntity.ok(service.actualizar(id, p));
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