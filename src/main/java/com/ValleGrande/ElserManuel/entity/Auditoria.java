package com.ValleGrande.ElserManuel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Clase base de auditoría.
 * Provee los 4 campos de auditoría requeridos:
 *   creadoPor, creadoEn, modificadoPor, modificadoEn
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditoria {

    @CreatedBy
    @Column(name = "creado_por", updatable = false, length = 100)
    private String creadoPor;

    @CreatedDate
    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn;

    @LastModifiedBy
    @Column(name = "modificado_por", length = 100)
    private String modificadoPor;

    @LastModifiedDate
    @Column(name = "modificado_en")
    private LocalDateTime modificadoEn;
}