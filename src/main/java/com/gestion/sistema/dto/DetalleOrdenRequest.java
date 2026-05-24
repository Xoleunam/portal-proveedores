package com.gestion.sistema.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DetalleOrdenRequest {
    private String descripcion;
    private Integer cantidad;
    private BigDecimal precioUnitario;
}