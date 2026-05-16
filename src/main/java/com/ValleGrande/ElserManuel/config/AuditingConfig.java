package com.ValleGrande.ElserManuel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/**
 * Activa el sistema de auditoría automática de Spring JPA.
 * Por ahora usa "sistema" como usuario fijo.
 * Cuando integres Spring Security, reemplaza el bean por el usuario autenticado.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditingConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        // TODO: reemplazar con SecurityContextHolder cuando agregues autenticación
        return () -> Optional.of("sistema");
    }
}