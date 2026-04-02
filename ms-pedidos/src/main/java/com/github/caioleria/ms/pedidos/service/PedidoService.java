package com.github.caioleria.ms.pedidos.service;

import com.github.caioleria.ms.pedidos.dto.ItemDoPedidoDto;
import com.github.caioleria.ms.pedidos.dto.PedidoDto;
import com.github.caioleria.ms.pedidos.entities.ItemDoPedido;
import com.github.caioleria.ms.pedidos.entities.Pedido;
import com.github.caioleria.ms.pedidos.entities.Status;
import com.github.caioleria.ms.pedidos.exceptions.ResourceNotFoundException;
import com.github.caioleria.ms.pedidos.repositories.ItemDoPedidoRepository;
import com.github.caioleria.ms.pedidos.repositories.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {
@Autowired
    private PedidoRepository pedidoRepository;

@Autowired
private ItemDoPedidoRepository itemDoPedidoRepository;
@Transactional
    public List<PedidoDto> findAllPedidos(){

    return pedidoRepository.findAll().stream().map(PedidoDto::new).toList();
    }
@Transactional
    public PedidoDto findPedidoById(Long id){

        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado" + id));
      return new PedidoDto(pedido);
    }

    @Transactional
    public PedidoDto savePedido (PedidoDto pedidoDTO){

    Pedido pedido = new Pedido();
    pedido.setData(LocalDate.now());
    pedido.setStatus(Status.CRIADO);
    mapDtoToPedido(pedidoDTO, pedido);
    pedido.calcularValorTotal();
    pedido = pedidoRepository.save(pedido);
    return new PedidoDto(pedido);
    }

    @Transactional
    public PedidoDto updatePedido(Long id, PedidoDto pedidoDto){
    try{
        Pedido pedido = pedidoRepository.getReferenceById(id);
        pedido.getItemsDoPedido().clear();
        pedido.setData(LocalDate.now());
        pedido.setStatus(Status.CRIADO);
        mapDtoToPedido(pedidoDto, pedido);
        pedido.calcularValorTotal();
        pedido = pedidoRepository.save(pedido);
        return new PedidoDto(pedido);
    }
    catch (EntityNotFoundException e){
        throw new EntityNotFoundException("pedido não encontrado");
    }

    }
    public void deletePedido(Long id){
    if (!pedidoRepository.existsById(id)){
        throw new EntityNotFoundException("pedido não encontrado");
    }
        pedidoRepository.deleteById(id);
    }

    private void mapDtoToPedido(PedidoDto pedidoDto, Pedido pedido){
    pedido.setCpf(pedidoDto.getCpf());
    pedido.setNome(pedidoDto.getNome());
    for(ItemDoPedidoDto itemDto : pedidoDto.getItens()){
        ItemDoPedido item = new ItemDoPedido();
        item.setPedido(pedido);
        item.setDescricao(itemDto.getDescricao());
        item.setQuantidade(itemDto.getQuantidade());
        item.setPrecoUnitario(itemDto.getPrecoUnitario());
        pedido.getItemsDoPedido().add(item);
    }
    }

}
