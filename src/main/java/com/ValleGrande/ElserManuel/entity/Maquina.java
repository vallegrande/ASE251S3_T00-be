package com.ValleGrande.ElserManuel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "maquinas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Maquina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    // SOLUCIÓN: Agregamos un valor por defecto a nivel de SQL
    @Column(nullable = false, length = 50, columnDefinition = "varchar(50) default 'Sin marca'")
    private String marca;

    @Column(nullable = false, length = 50)
    private String modelo;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precio;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    // SOLUCIÓN: Agregamos un valor por defecto para el booleano
    @Column(nullable = false, columnDefinition = "bit default 1")
    @Builder.Default
    private Boolean estado = true;
}