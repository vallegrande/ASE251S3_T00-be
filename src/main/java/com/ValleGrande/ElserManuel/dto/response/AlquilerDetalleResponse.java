package com.ValleGrande.ElserManuel.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO RESPONSE - Línea de detalle del alquiler.
 * Lo que se devuelve al cliente con los datos calculados.
 */
@Data
@Builder
public class AlquilerDetalleResponse {

    private Long id;
    private Long maquinaId;
    private String maquinaNombre;
    private String maquinaMarca;
    private Integer cantidadDias;
    private BigDecimal precioDia;
    private BigDecimal subtotal;
}