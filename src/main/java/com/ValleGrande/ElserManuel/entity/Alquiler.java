package com.ValleGrande.ElserManuel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "alquileres")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Alquiler extends Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String numeroAlquiler;       // ALQ-0001, autogenerado

    @Column(nullable = false)
    private LocalDate fechaAlquiler;     // autocalculado = hoy

    @Column(nullable = false)
    private LocalDate fechaDevolucion;   // viene del request

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    @Column(length = 500)
    private String observaciones;

    @Column(nullable = false, precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal subtotal = BigDecimal.ZERO;   // autocalculado

    @Column(nullable = false, precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal igv = BigDecimal.ZERO;        // 18%, autocalculado

    @Column(nullable = false, precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal total = BigDecimal.ZERO;      // autocalculado

    @Column(nullable = false, columnDefinition = "bit default 1")
    @Builder.Default
    private Boolean estado = true;

    @OneToMany(mappedBy = "alquiler", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Builder.Default
    private List<AlquilerDetalle> detalles = new ArrayList<>();
}