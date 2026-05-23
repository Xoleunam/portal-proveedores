package com.gestion.sistema.service;

import com.gestion.sistema.entity.RegimenFiscal;
import com.gestion.sistema.entity.Proveedor;
import com.gestion.sistema.entity.Usuario;
import com.gestion.sistema.repository.ProveedorRepository;
import com.gestion.sistema.repository.UsuarioRepository;
import com.gestion.sistema.repository.RegimenFiscalRepository;
import com.gestion.sistema.dto.ProveedorRequest;
import com.gestion.sistema.dto.ProveedorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final RegimenFiscalRepository regimenFiscalRepository;
    private final UsuarioRepository usuarioRepository;

    public ProveedorResponse crear(ProveedorRequest request, String email) {
        if(proveedorRepository.existsByRfc(request.getRfc())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "RFC ya existe");
        }

        RegimenFiscal regimen = regimenFiscalRepository.findById(request.getRegimenFiscalId())
                .orElseThrow(() -> new RuntimeException("No existe el regimen fiscal"));

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Proveedor proveedor = new Proveedor();
        proveedor.setRfc(request.getRfc());
        proveedor.setEmail(email);
        proveedor.setRazonSocial(request.getRazonSocial());
        proveedor.setTelefono(request.getTelefono());
        proveedor.setDireccion(request.getDireccion());
        proveedor.setRegimenFiscal(regimen);
        proveedor.setUsuario(usuario);

        return ProveedorResponse.fromEntity(proveedorRepository.save(proveedor));
    }

    public List<ProveedorResponse> obtenerTodos() {
        return proveedorRepository.findAll()
                .stream()
                .map(ProveedorResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public ProveedorResponse obtenerPorId(Long id)  {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("Usuario no encontrado"));

                return ProveedorResponse.fromEntity(proveedor);
    }

    public ProveedorResponse cambiarEstado(Long id, Proveedor.EstadoProveedor estado) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Provedor no encontrado"));
        proveedor.setEstado(estado);
        return ProveedorResponse.fromEntity(proveedorRepository.save(proveedor));
    }

    public List<ProveedorResponse> obtenerPorEstado(Proveedor.EstadoProveedor estado) {
        return proveedorRepository.findByEstado(estado)
                .stream()
                .map(ProveedorResponse::fromEntity)
                .collect(Collectors.toList());
    }

}


