package com.github.caioleria.ms.pedidos.service;

import com.github.caioleria.ms.pedidos.dto.PedidoDto;
import com.github.caioleria.ms.pedidos.entities.Pedido;
import com.github.caioleria.ms.pedidos.exceptions.ResourceNotFoundException;
import com.github.caioleria.ms.pedidos.repositories.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {
@Autowired
    private PedidoRepository pedidoRepository;

@Transactional
    public List<PedidoDto> findAllPedidos(){

    return pedidoRepository.findAll().stream().map(PedidoDto::new).toList();
    }
@Transactional
    public PedidoDto findPedidoById(Long id){

        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado" + id));
      return new PedidoDto(pedido);
    }
}
