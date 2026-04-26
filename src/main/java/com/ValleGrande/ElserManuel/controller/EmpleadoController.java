package com.ValleGrande.ElserManuel.controller;

import com.ValleGrande.ElserManuel.entity.Empleado;
import com.ValleGrande.ElserManuel.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/empleados")
public class EmpleadoController {
    @Autowired private EmpleadoService service;

    @GetMapping
    public List<Empleado> listar() { return service.listarActivos(); }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtener(@PathVariable Long id) {
        return service.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Empleado guardar(@RequestBody Empleado e) { return service.guardar(e); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarLongicamente(id);
        return ResponseEntity.noContent().build();
    }
}
