package com.ValleGrande.ElserManuel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Campos propios (8):
 *   1. id           → Long
 *   2. nombre       → String
 *   3. apellido     → String
 *   4. cargo        → String
 *   5. sueldo       → BigDecimal
 *   6. dni          → String
 *   7. fechaIngreso → LocalDate
 *   8. estado       → Boolean
 *
 * Campos de auditoría heredados (4):
 *   9.  creadoPor
 *   10. creadoEn
 *   11. modificadoPor
 *   12. modificadoEn
 *
 * Tipos de datos usados (≥ 4): Long, String, BigDecimal, LocalDate, Boolean
 */
@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Empleado extends Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                            // 1 - Long

    @Column(nullable = false, length = 100)
    private String nombre;                      // 2 - String

    @Column(nullable = false, length = 100)
    private String apellido;                    // 3 - String

    @Column(nullable = false, length = 50)
    private String cargo;                       // 4 - String

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal sueldo;                  // 5 - BigDecimal

    @Column(nullable = false, unique = true, length = 20)
    private String dni;                         // 6 - String

    @Column(nullable = false)
    private LocalDate fechaIngreso;             // 7 - LocalDate

    @Column(nullable = false, columnDefinition = "bit default 1")
    @Builder.Default
    private Boolean estado = true;              // 8 - Boolean
}