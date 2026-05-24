package com.ValleGrande.ElserManuel.controller;

import com.ValleGrande.ElserManuel.dto.request.AlquilerRequest;
import com.ValleGrande.ElserManuel.dto.response.AlquilerResponse;
import com.ValleGrande.ElserManuel.service.AlquilerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alquileres")
@Tag(name = "Alquileres", description = "Transacción: alquiler de máquinas (cabecera + detalle)")
public class AlquilerController {

    @Autowired
    private AlquilerService service;

    /**
     * POST /api/v1/alquileres
     * Registra un alquiler completo (cabecera + detalle) en una sola petición.
     */
    @PostMapping
    @Operation(summary = "Registrar alquiler", description = "Crea el alquiler con su cabecera y detalle en una sola acción")
    public ResponseEntity<?> registrar(@RequestBody AlquilerRequest request) {
        try {
            AlquilerResponse response = service.registrar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * GET /api/v1/alquileres
     * Lista todos los alquileres activos con su detalle.
     */
    @GetMapping
    @Operation(summary = "Listar alquileres", description = "Retorna todos los alquileres activos con sus detalles")
    public List<AlquilerResponse> listar() {
        return service.listar();
    }

    /**
     * GET /api/v1/alquileres/{id}
     * Obtiene un alquiler por ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener alquiler por ID")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/v1/alquileres/{id}
     * Anula (eliminación lógica) un alquiler.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Anular alquiler", description = "Anulación lógica del alquiler (estado = false)")
    public ResponseEntity<Void> anular(@PathVariable Long id) {
        service.anular(id);
        return ResponseEntity.noContent().build();
    }
}