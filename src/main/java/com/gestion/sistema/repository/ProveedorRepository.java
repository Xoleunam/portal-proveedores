package com.gestion.sistema.repository;

import com.gestion.sistema.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor,Long> {
Optional<Proveedor> findByRfc(String rfc);
Boolean existsByRfc(String rfc);
List<Proveedor> findByEstado(Proveedor.EstadoProveedor estado);
}
