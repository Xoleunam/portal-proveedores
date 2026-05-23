package com.gestion.sistema.dto;

import lombok.Data;

@Data
public class ProveedorRequest {
    private String rfc;
    private String razonSocial;
    private String email;
    private String telefono;
    private String direccion;
    private Long regimenFiscalId;
}
