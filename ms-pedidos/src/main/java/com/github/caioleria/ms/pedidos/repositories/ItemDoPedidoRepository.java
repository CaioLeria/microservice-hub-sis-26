package com.github.caioleria.ms.pedidos.repositories;

import com.github.caioleria.ms.pedidos.entities.ItemDoPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDoPedidoRepository extends JpaRepository<ItemDoPedido,Long> {
}
