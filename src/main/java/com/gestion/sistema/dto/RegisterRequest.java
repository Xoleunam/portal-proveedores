package com.gestion.sistema.dto;

import com.gestion.sistema.entity.Usuario;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String nombre;
    private String apellido;
    private Usuario.Rol rol;
}