package com.gestion.sistema.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "regimenes_fiscales")
@Data
public class RegimenFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String clave;

    @Column(nullable = false, length = 150)
    private String descripcion;

    @Column(nullable = false)
    private Boolean activo = true;
}