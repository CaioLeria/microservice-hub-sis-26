package com.github.caioleria.ms.pedidos.controller;

import com.github.caioleria.ms.pedidos.dto.PedidoDto;
import com.github.caioleria.ms.pedidos.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<PedidoDto> createPedido (@RequestBody @Valid PedidoDto pedidoDto){
        pedidoDto = pedidoService.savePedido(pedidoDto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(pedidoDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(pedidoDto);
    }
    @PutMapping
    public ResponseEntity<PedidoDto> updatePedido(@PathVariable Long id, @RequestBody @Valid PedidoDto pedidoDto){
        pedidoDto = pedidoService.updatePedido(id, pedidoDto);
        return ResponseEntity.ok(pedidoDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido (@PathVariable Long id){
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{pedidoId}/pagamento/confirmado")
    public void confirmarPagamento(@PathVariable Long pedidoId){
        pedidoService.confirmarPagamento(pedidoId);
    }
}
