package com.ValleGrande.ElserManuel.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO RESPONSE - Cabecera completa del alquiler.
 * Incluye todos los campos calculados y los detalles.
 */
@Data
@Builder
public class AlquilerResponse {

    private Long id;
    private String numeroAlquiler;
    private LocalDate fechaAlquiler;
    private LocalDate fechaDevolucion;

    // Datos del cliente
    private Long clienteId;
    private String clienteNombreCompleto;
    private String clienteDocumento;

    // Datos del empleado
    private Long empleadoId;
    private String empleadoNombreCompleto;

    private String observaciones;

    // Montos calculados
    private BigDecimal subtotal;
    private BigDecimal igv;
    private BigDecimal total;

    private Boolean estado;
    private LocalDateTime creadoEn;

    // Detalles
    private List<AlquilerDetalleResponse> detalles;
}