package com.gestion.sistema.service;

import com.gestion.sistema.dto.AuthResponse;
import com.gestion.sistema.dto.LoginRequest;
import com.gestion.sistema.dto.RegisterRequest;
import com.gestion.sistema.entity.Usuario;
import com.gestion.sistema.repository.UsuarioRepository;
import com.gestion.sistema.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtService.generateToken(
                usuario.getEmail(),
                usuario.getRol().name()
        );

        return new AuthResponse(token, usuario.getEmail(), usuario.getRol().name());
    }

    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setRol(request.getRol());

        usuarioRepository.save(usuario);

        String token = jwtService.generateToken(
                usuario.getEmail(),
                usuario.getRol().name()
        );

        return new AuthResponse(token, usuario.getEmail(), usuario.getRol().name());
    }
}