package com.ValleGrande.ElserManuel.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO REQUEST - Cabecera del alquiler.
 * Lo que el cliente/frontend envía para registrar un alquiler.
 *
 * Campos autocalculados (NO vienen del request):
 *   - numeroAlquiler (lo genera el servicio)
 *   - fechaAlquiler  (= LocalDate.now())
 *   - subtotal, igv, total (se calculan con los detalles)
 */
@Data
public class AlquilerRequest {

    private Long clienteId;          // FK → clientes
    private Long empleadoId;         // FK → empleados
    private LocalDate fechaDevolucion;
    private String observaciones;

    private List<AlquilerDetalleRequest> detalles;  // al menos 1 línea
}