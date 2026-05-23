package com.gestion.sistema.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "proveedores")
@Data
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 13)
    private String rfc;

    @Column(nullable = false, length = 150)
    private String razonSocial;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(length = 15)
    private String telefono;

    @Column(length = 255)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "regimen_fiscal_id", nullable = false)
    private RegimenFiscal regimenFiscal;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoProveedor estado = EstadoProveedor.PENDIENTE;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        fechaRegistro = LocalDateTime.now();
    }

    public enum EstadoProveedor {
        PENDIENTE, APROBADO, RECHAZADO, INACTIVO
    }
}