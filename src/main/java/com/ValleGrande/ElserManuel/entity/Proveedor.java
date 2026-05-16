package com.ValleGrande.ElserManuel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Campos propios (8):
 *   1. id              → Long
 *   2. razonSocial     → String
 *   3. ruc             → String
 *   4. telefono        → String
 *   5. correo          → String
 *   6. direccion       → String
 *   7. creditoMaximo   → BigDecimal
 *   8. estado          → Boolean
 *
 * Campos de auditoría heredados (4):
 *   9.  creadoPor
 *   10. creadoEn
 *   11. modificadoPor
 *   12. modificadoEn
 *
 * Tipos de datos usados (≥ 4): Long, String, BigDecimal, Boolean
 */
@Entity
@Table(name = "proveedores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Proveedor extends Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                    // 1 - Long

    @Column(nullable = false, length = 150)
    private String razonSocial;                         // 2 - String

    @Column(nullable = false, unique = true, length = 20)
    private String ruc;                                 // 3 - String

    @Column(length = 20)
    private String telefono;                            // 4 - String

    @Column(length = 150)
    private String correo;                              // 5 - String

    @Column(length = 200)
    private String direccion;                           // 6 - String

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal creditoMaximo;                   // 7 - BigDecimal

    @Column(nullable = false, columnDefinition = "bit default 1")
    @Builder.Default
    private Boolean estado = true;                      // 8 - Boolean
}