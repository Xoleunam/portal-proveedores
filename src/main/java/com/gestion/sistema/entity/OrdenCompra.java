package com.gestion.sistema.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.sql.ast.tree.from.MappedByTableGroup;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "ordenes_compra")
@Data
public class OrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String numeroOrden;

    @ManyToOne
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoOrden estado = EstadoOrden.BORRADOR;

    @Column(precision = 10, scale = 2)
    private BigDecimal subTotal =  BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal iva = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @Column(length = 500)
    private String observaciones;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "ordenCompra", cascade = CascadeType.ALL)
    private List<DetalleOrden> detalles;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum EstadoOrden {
        BORRADOR, ENVIADA, APROBADA, RECHAZADA, COMPLETADA, CANCELADA
    }
}
