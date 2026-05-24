package com.ValleGrande.ElserManuel.dto.request;

import lombok.Data;

/**
 * DTO REQUEST - Línea de detalle del alquiler.
 * El cliente sólo envía: maquinaId y cantidadDias.
 * El precio y subtotal se calculan en el servicio.
 */
@Data
public class AlquilerDetalleRequest {

    private Long maquinaId;       // ID de la máquina a alquilar
    private Integer cantidadDias; // cuántos días se alquila
}