package com.gestion.sistema.controller;

import com.gestion.sistema.dto.ProveedorRequest;
import com.gestion.sistema.dto.ProveedorResponse;
import com.gestion.sistema.entity.Proveedor;
import com.gestion.sistema.service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    @PostMapping
    public ResponseEntity<ProveedorResponse> crear(
            @RequestBody ProveedorRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(proveedorService.crear(request, userDetails.getUsername()));
    }

    @GetMapping
    public ResponseEntity<List<ProveedorResponse>> obtenerTodos() {
        return ResponseEntity.ok(proveedorService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(proveedorService.obtenerPorId(id));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ProveedorResponse> cambiarEstado(
            @PathVariable Long id,
            @RequestParam Proveedor.EstadoProveedor estado) {
        return ResponseEntity.ok(proveedorService.cambiarEstado(id, estado));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ProveedorResponse>> obtenerPorEstado(
            @PathVariable Proveedor.EstadoProveedor estado) {
        return ResponseEntity.ok(proveedorService.obtenerPorEstado(estado));
    }

}

