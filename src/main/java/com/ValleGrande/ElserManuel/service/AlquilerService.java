package com.ValleGrande.ElserManuel.service;

import com.ValleGrande.ElserManuel.dto.request.AlquilerDetalleRequest;
import com.ValleGrande.ElserManuel.dto.request.AlquilerRequest;
import com.ValleGrande.ElserManuel.dto.response.AlquilerDetalleResponse;
import com.ValleGrande.ElserManuel.dto.response.AlquilerResponse;
import com.ValleGrande.ElserManuel.entity.Alquiler;
import com.ValleGrande.ElserManuel.entity.AlquilerDetalle;
import com.ValleGrande.ElserManuel.entity.Cliente;
import com.ValleGrande.ElserManuel.entity.Empleado;
import com.ValleGrande.ElserManuel.entity.Maquina;
import com.ValleGrande.ElserManuel.repository.AlquilerRepository;
import com.ValleGrande.ElserManuel.repository.ClienteRepository;
import com.ValleGrande.ElserManuel.repository.EmpleadoRepository;
import com.ValleGrande.ElserManuel.repository.MaquinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlquilerService {

    private static final BigDecimal IGV_TASA = new BigDecimal("0.18");

    @Autowired private AlquilerRepository alquilerRepository;
    @Autowired private ClienteRepository  clienteRepository;
    @Autowired private EmpleadoRepository empleadoRepository;
    @Autowired private MaquinaRepository  maquinaRepository;

    // -------------------------------------------------------
    // REGISTRAR TRANSACCIÓN (cabecera + detalle en una sola acción)
    // -------------------------------------------------------
    @Transactional
    public AlquilerResponse registrar(AlquilerRequest request) {

        // 1. Validar cliente activo
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .filter(Cliente::getEstado)
                .orElseThrow(() -> new RuntimeException(
                        "Cliente no encontrado o inactivo: id=" + request.getClienteId()));

        // 2. Validar empleado activo
        Empleado empleado = empleadoRepository.findById(request.getEmpleadoId())
                .filter(Empleado::getEstado)
                .orElseThrow(() -> new RuntimeException(
                        "Empleado no encontrado o inactivo: id=" + request.getEmpleadoId()));

        // 3. Validar fecha de devolución
        if (request.getFechaDevolucion() == null ||
            !request.getFechaDevolucion().isAfter(LocalDate.now())) {
            throw new RuntimeException("La fecha de devolución debe ser posterior a hoy.");
        }

        // 4. Validar que haya al menos un detalle
        if (request.getDetalles() == null || request.getDetalles().isEmpty()) {
            throw new RuntimeException("El alquiler debe tener al menos una máquina en el detalle.");
        }

        // 5. Construir la cabecera
        Alquiler alquiler = Alquiler.builder()
                .numeroAlquiler(generarNumeroAlquiler())
                .fechaAlquiler(LocalDate.now())           // autocalculado
                .fechaDevolucion(request.getFechaDevolucion())
                .cliente(cliente)
                .empleado(empleado)
                .observaciones(request.getObservaciones())
                .estado(true)
                .build();

        // 6. Construir los detalles y calcular totales
        List<AlquilerDetalle> detalles = new ArrayList<>();
        BigDecimal subtotalTotal = BigDecimal.ZERO;

        for (AlquilerDetalleRequest detalleReq : request.getDetalles()) {

            // Validar máquina activa
            Maquina maquina = maquinaRepository.findById(detalleReq.getMaquinaId())
                    .filter(Maquina::getEstado)
                    .orElseThrow(() -> new RuntimeException(
                            "Máquina no encontrada o inactiva: id=" + detalleReq.getMaquinaId()));

            if (detalleReq.getCantidadDias() == null || detalleReq.getCantidadDias() <= 0) {
                throw new RuntimeException("La cantidad de días debe ser mayor a 0.");
            }

            // El precio_dia se toma del precio registrado en la máquina
            BigDecimal precioDia = maquina.getPrecio();
            BigDecimal subtotalLinea = precioDia
                    .multiply(BigDecimal.valueOf(detalleReq.getCantidadDias()))
                    .setScale(2, RoundingMode.HALF_UP);

            AlquilerDetalle detalle = AlquilerDetalle.builder()
                    .alquiler(alquiler)
                    .maquina(maquina)
                    .cantidadDias(detalleReq.getCantidadDias())
                    .precioDia(precioDia)
                    .subtotal(subtotalLinea)
                    .build();

            detalles.add(detalle);
            subtotalTotal = subtotalTotal.add(subtotalLinea);
        }

        // 7. Calcular IGV y total (autocalculados)
        BigDecimal igv   = subtotalTotal.multiply(IGV_TASA).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subtotalTotal.add(igv).setScale(2, RoundingMode.HALF_UP);

        alquiler.setSubtotal(subtotalTotal);
        alquiler.setIgv(igv);
        alquiler.setTotal(total);
        alquiler.setDetalles(detalles);

        // 8. Guardar en BD (cascade guarda los detalles también)
        Alquiler guardado = alquilerRepository.save(alquiler);

        return toResponse(guardado);
    }

    // -------------------------------------------------------
    // LISTAR TRANSACCIONES
    // -------------------------------------------------------
    @Transactional(readOnly = true)
    public List<AlquilerResponse> listar() {
        return alquilerRepository.findByEstadoTrueOrderByIdDesc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // -------------------------------------------------------
    // OBTENER POR ID
    // -------------------------------------------------------
    @Transactional(readOnly = true)
    public AlquilerResponse obtenerPorId(Long id) {
        Alquiler alquiler = alquilerRepository.findById(id)
                .filter(Alquiler::getEstado)
                .orElseThrow(() -> new RuntimeException("Alquiler no encontrado: id=" + id));
        return toResponse(alquiler);
    }

    // -------------------------------------------------------
    // ANULAR (eliminado lógico)
    // -------------------------------------------------------
    @Transactional
    public void anular(Long id) {
        Alquiler alquiler = alquilerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alquiler no encontrado: id=" + id));
        alquiler.setEstado(false);
        alquilerRepository.save(alquiler);
    }

    // -------------------------------------------------------
    // HELPERS
    // -------------------------------------------------------

    /** Genera el siguiente número de alquiler: ALQ-0001, ALQ-0002 … */
    private String generarNumeroAlquiler() {
        return alquilerRepository.findTopByOrderByIdDesc()
                .map(a -> {
                    // Extrae el número del último y le suma 1
                    String num = a.getNumeroAlquiler().replace("ALQ-", "");
                    int siguiente = Integer.parseInt(num) + 1;
                    return String.format("ALQ-%04d", siguiente);
                })
                .orElse("ALQ-0001");
    }

    /** Convierte entidad → DTO de respuesta */
    private AlquilerResponse toResponse(Alquiler a) {
        List<AlquilerDetalleResponse> detallesResp = a.getDetalles().stream()
                .map(d -> AlquilerDetalleResponse.builder()
                        .id(d.getId())
                        .maquinaId(d.getMaquina().getId())
                        .maquinaNombre(d.getMaquina().getNombre())
                        .maquinaMarca(d.getMaquina().getMarca())
                        .cantidadDias(d.getCantidadDias())
                        .precioDia(d.getPrecioDia())
                        .subtotal(d.getSubtotal())
                        .build())
                .collect(Collectors.toList());

        return AlquilerResponse.builder()
                .id(a.getId())
                .numeroAlquiler(a.getNumeroAlquiler())
                .fechaAlquiler(a.getFechaAlquiler())
                .fechaDevolucion(a.getFechaDevolucion())
                .clienteId(a.getCliente().getId())
                .clienteNombreCompleto(a.getCliente().getNombre() + " " + a.getCliente().getApellido())
                .clienteDocumento(a.getCliente().getDocumento())
                .empleadoId(a.getEmpleado().getId())
                .empleadoNombreCompleto(a.getEmpleado().getNombre() + " " + a.getEmpleado().getApellido())
                .observaciones(a.getObservaciones())
                .subtotal(a.getSubtotal())
                .igv(a.getIgv())
                .total(a.getTotal())
                .estado(a.getEstado())
                .creadoEn(a.getCreadoEn())
                .detalles(detallesResp)
                .build();
    }
}