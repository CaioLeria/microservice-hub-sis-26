package com.github.caioleria.ms.pedidos.service;

import com.github.caioleria.ms.pedidos.dto.PedidoDto;
import com.github.caioleria.ms.pedidos.repositories.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
}
