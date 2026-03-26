package com.github.caioleria.ms.pedidos.controller;

import com.github.caioleria.ms.pedidos.dto.PedidoDto;
import com.github.caioleria.ms.pedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoDto>> getAllPedidos(){
        List<PedidoDto> pedidos = pedidoService.findAllPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDto> gePedidosById(@PathVariable Long id){
        PedidoDto pedido = pedidoService.findPedidoById(id);
        return ResponseEntity.ok(pedido);
    }
}
