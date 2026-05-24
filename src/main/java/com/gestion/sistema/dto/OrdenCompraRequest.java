package com.gestion.sistema.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrdenCompraRequest {
    private Long proveedorId;
    private String observaciones;
    private List<DetalleOrdenRequest> detalles;
}