package com.gestion.sistema.repository;

import com.gestion.sistema.entity.RegimenFiscal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RegimenFiscalRepository extends JpaRepository<RegimenFiscal, Long>{
    Optional<RegimenFiscal> findByClave(String clave);
}
