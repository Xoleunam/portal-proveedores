package com.gestion.sistema.dto;

import com.gestion.sistema.entity.OrdenCompra;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrdenCompraResponse {
    private Long id;
    private String numeroOrden;
    private String proveedor;
    private String estado;
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;
    private String observaciones;
    private LocalDateTime fechaCreacion;
    private List<DetalleOrdenResponse> detalles;

    @Data
    public static class DetalleOrdenResponse {
        private Long id;
        private String descripcion;
        private Integer cantidad;
        private BigDecimal precioUnitario;
        private BigDecimal subtotal;
    }

    public static OrdenCompraResponse fromEntity(OrdenCompra orden) {
        OrdenCompraResponse response = new OrdenCompraResponse();
        response.setId(orden.getId());
        response.setNumeroOrden(orden.getNumeroOrden());
        response.setProveedor(orden.getProveedor().getRazonSocial());
        response.setEstado(orden.getEstado().name());
        response.setSubtotal(orden.getSubTotal());
        response.setIva(orden.getIva());
        response.setTotal(orden.getTotal());
        response.setObservaciones(orden.getObservaciones());
        response.setFechaCreacion(orden.getCreatedAt());
        response.setDetalles(orden.getDetalles().stream()
                .map(d -> {
                    DetalleOrdenResponse dr = new DetalleOrdenResponse();
                    dr.setId(d.getId());
                    dr.setDescripcion(d.getDescripcion());
                    dr.setCantidad(d.getCantidad());
                    dr.setPrecioUnitario(d.getPrecioUnitario());
                    dr.setSubtotal(d.getSubtotal());
                    return dr;
                })
                .collect(Collectors.toList()));
        return response;
    }
}