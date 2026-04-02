package com.github.caioleria.ms.pagamentos.repositories;

import com.github.caioleria.ms.pagamentos.entities.Pagamentos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamentos, Long> {
}
