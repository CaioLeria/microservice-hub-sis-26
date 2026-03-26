package com.github.caioleria.ms.pedidos.repositories;

import com.github.caioleria.ms.pedidos.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> { }
