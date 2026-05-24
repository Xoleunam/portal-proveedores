package com.gestion.sistema.service;

import com.gestion.sistema.dto.DetalleOrdenRequest;
import com.gestion.sistema.dto.OrdenCompraRequest;
import com.gestion.sistema.dto.OrdenCompraResponse;
import com.gestion.sistema.entity.DetalleOrden;
import com.gestion.sistema.entity.OrdenCompra;
import com.gestion.sistema.entity.Proveedor;
import com.gestion.sistema.entity.Usuario;
import com.gestion.sistema.repository.OrdenCompraRepository;
import com.gestion.sistema.repository.ProveedorRepository;
import com.gestion.sistema.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdenCompraService {

    private final OrdenCompraRepository ordenCompraRepository;
    private final ProveedorRepository proveedorRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public OrdenCompraResponse crear(OrdenCompraRequest request, String email) {
        Proveedor proveedor = proveedorRepository.findById(request.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        if (!proveedor.getEstado().equals(Proveedor.EstadoProveedor.APROBADO)) {
            throw new RuntimeException("El proveedor no está aprobado");
        }

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        OrdenCompra orden = new OrdenCompra();
        orden.setNumeroOrden(generarNumeroOrden());
        orden.setProveedor(proveedor);
        orden.setUsuario(usuario);
        orden.setObservaciones(request.getObservaciones());

        List<DetalleOrden> detalles = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (DetalleOrdenRequest detalleRequest : request.getDetalles()) {
            DetalleOrden detalle = new DetalleOrden();
            detalle.setDescripcion(detalleRequest.getDescripcion());
            detalle.setCantidad(detalleRequest.getCantidad());
            detalle.setPrecioUnitario(detalleRequest.getPrecioUnitario());
            detalle.setOrdenCompra(orden);
            detalles.add(detalle);
            subtotal = subtotal.add(
                    detalleRequest.getPrecioUnitario()
                            .multiply(BigDecimal.valueOf(detalleRequest.getCantidad()))
            );
        }

        BigDecimal iva = subtotal.multiply(new BigDecimal("0.16"));
        BigDecimal total = subtotal.add(iva);

        orden.setDetalles(detalles);
        orden.setSubTotal(subtotal);
        orden.setIva(iva);
        orden.setTotal(total);

        return OrdenCompraResponse.fromEntity(ordenCompraRepository.save(orden));
    }

    @Transactional
    public List<OrdenCompraResponse> obtenerTodas() {
        return ordenCompraRepository.findAll()
                .stream()
                .map(OrdenCompraResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrdenCompraResponse obtenerPorId(Long id) {
        return OrdenCompraResponse.fromEntity(
                ordenCompraRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Orden no encontrada"))
        );
    }

    public OrdenCompraResponse cambiarEstado(Long id, OrdenCompra.EstadoOrden estado) {
        OrdenCompra orden = ordenCompraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        orden.setEstado(estado);
        return OrdenCompraResponse.fromEntity(ordenCompraRepository.save(orden));
    }

    public List<OrdenCompraResponse> obtenerPorProveedor(Long proveedorId) {
        return ordenCompraRepository.findByProveedorId(proveedorId)
                .stream()
                .map(OrdenCompraResponse::fromEntity)
                .collect(Collectors.toList());
    }

    private String generarNumeroOrden() {
        String fecha = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = ordenCompraRepository.count() + 1;
        return "OC-" + fecha + "-" + String.format("%04d", count);
    }
}