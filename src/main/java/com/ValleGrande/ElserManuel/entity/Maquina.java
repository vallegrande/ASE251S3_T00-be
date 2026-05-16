package com.ValleGrande.ElserManuel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Campos propios (8):
 *   1. id               → Long
 *   2. nombre           → String
 *   3. marca            → String
 *   4. modelo           → String
 *   5. precio           → BigDecimal
 *   6. fechaAdquisicion → LocalDate   ← NUEVO
 *   7. categoria        → Categoria   (FK)
 *   8. estado           → Boolean
 *
 * Campos de auditoría heredados (4):
 *   9.  creadoPor
 *   10. creadoEn
 *   11. modificadoPor
 *   12. modificadoEn
 *
 * Tipos de datos usados (≥ 4): Long, String, BigDecimal, LocalDate, Boolean, Categoria(FK)
 */
@Entity
@Table(name = "maquinas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Maquina extends Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                        // 1 - Long

    @Column(nullable = false, length = 100)
    private String nombre;                                  // 2 - String

    @Column(nullable = false, length = 50,
            columnDefinition = "varchar(50) default 'Sin marca'")
    private String marca;                                   // 3 - String

    @Column(nullable = false, length = 50)
    private String modelo;                                  // 4 - String

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precio;                              // 5 - BigDecimal

    @Column(nullable = false)
    private LocalDate fechaAdquisicion;                     // 6 - LocalDate

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;                            // 7 - FK (Categoria)

    @Column(nullable = false, columnDefinition = "bit default 1")
    @Builder.Default
    private Boolean estado = true;                          // 8 - Boolean
}