package com.gestion.sistema.dto;

import com.gestion.sistema.entity.Proveedor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProveedorResponse {
    private Long id;
    private String rfc;
    private String razonSocial;
    private String email;
    private String telefono;
    private String direccion;
    private String regimenFiscal;
    private String estado;
    private LocalDateTime fechaRegistro;

    public static ProveedorResponse fromEntity(Proveedor proveedor) {
        ProveedorResponse response = new ProveedorResponse();
        response.setId(proveedor.getId());
        response.setRfc(proveedor.getRfc());
        response.setRazonSocial(proveedor.getRazonSocial());
        response.setEmail(proveedor.getEmail());
        response.setTelefono(proveedor.getTelefono());
        response.setDireccion(proveedor.getDireccion());
        response.setRegimenFiscal(proveedor.getRegimenFiscal().getDescripcion());
        response.setEstado(proveedor.getEstado().name());
        response.setFechaRegistro(proveedor.getFechaRegistro());
        return response;
    }
}