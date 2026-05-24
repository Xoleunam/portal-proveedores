package com.gestion.sistema.controller;

import com.gestion.sistema.dto.OrdenCompraRequest;
import com.gestion.sistema.dto.OrdenCompraResponse;
import com.gestion.sistema.entity.OrdenCompra;
import com.gestion.sistema.service.OrdenCompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
public class OrdenCompraController {

    private final OrdenCompraService ordenCompraService;

    @PostMapping
    public ResponseEntity<OrdenCompraResponse> crear(
            @RequestBody OrdenCompraRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ordenCompraService.crear(request, userDetails.getUsername()));
    }

    @GetMapping
    public ResponseEntity<List<OrdenCompraResponse>> obtenerTodas() {
        return ResponseEntity.ok(ordenCompraService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenCompraResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ordenCompraService.obtenerPorId(id));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<OrdenCompraResponse> cambiarEstado(
            @PathVariable Long id,
            @RequestParam OrdenCompra.EstadoOrden estado) {
        return ResponseEntity.ok(ordenCompraService.cambiarEstado(id, estado));
    }

    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<List<OrdenCompraResponse>> obtenerPorProveedor(
            @PathVariable Long proveedorId) {
        return ResponseEntity.ok(ordenCompraService.obtenerPorProveedor(proveedorId));
    }
}