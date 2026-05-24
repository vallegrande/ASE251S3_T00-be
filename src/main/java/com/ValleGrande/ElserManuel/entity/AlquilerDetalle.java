package com.ValleGrande.ElserManuel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * DETALLE de la transacción AlquilerMaquina.
 * Campos propios:
 *   1. id            → Long
 *   2. alquiler      → Alquiler (FK cabecera)
 *   3. maquina       → Maquina  (FK maestro)
 *   4. cantidadDias  → Integer
 *   5. precioDia     → BigDecimal (precio al momento del alquiler)
 *   6. subtotal      → BigDecimal (autocalculado: cantidadDias * precioDia)
 */
@Entity
@Table(name = "alquiler_detalles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class AlquilerDetalle extends Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alquiler_id", nullable = false)
    private Alquiler alquiler;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "maquina_id", nullable = false)
    private Maquina maquina;

    @Column(nullable = false)
    private Integer cantidadDias;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precioDia;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;  // autocalculado
}