package com.github.caioleria.ms.pagamentos.controller;

import com.github.caioleria.ms.pagamentos.dto.PagamentoDto;
import com.github.caioleria.ms.pagamentos.entities.Pagamentos;
import com.github.caioleria.ms.pagamentos.service.PagamentoService;
import feign.Response;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pagamentos")
public class PagamentosController {
    @Autowired
    private PagamentoService pagamentoService;

    @PatchMapping("/{id}/confirmar")
    @CircuitBreaker(name = "atualizarPedido", fallbackMethod = "fallbackCorfimarPagamentoPendende")
    public ResponseEntity<PagamentoDto> confirmarPagamentoDoPedido(@PathVariable @NotNull Long id){
        PagamentoDto dto = pagamentoService.confirmarPagamentoDoPedido(id);
        return ResponseEntity.ok(dto);
    }

   public ResponseEntity<PagamentoDto> fallbackCorfimarPagamentoPendende(Long id, Throwable e){
       log.error("Falha ao confirmar pedido {}. Ativando fallback. Erro {}", id, e.getMessage());
      PagamentoDto dto = pagamentoService.alterarStatusDoPagamento(id);
        return ResponseEntity.status(503).body(dto);
   }

    @GetMapping
    public ResponseEntity<List<PagamentoDto>> getAll (){
        List<PagamentoDto> pagamentos = pagamentoService.findAllPagamentos();
        return ResponseEntity.ok(pagamentos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDto> getById(@PathVariable Long id){
        PagamentoDto pagamento = pagamentoService.findById(id);
        return ResponseEntity.ok(pagamento);
    }

    @PostMapping
    public ResponseEntity<PagamentoDto> save (@RequestBody @Valid PagamentoDto pagamento){
        pagamento = pagamentoService.savePagamento(pagamento);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(pagamento.getId())
                .toUri();
        return ResponseEntity.created(uri).body(pagamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDto> update (@PathVariable Long id, @RequestBody @Valid PagamentoDto pagamento){

        pagamento = pagamentoService.updatePagamento(id, pagamento);
        return ResponseEntity.ok(pagamento);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@Valid Long id){
        pagamentoService.deletePagamento(id);
        return ResponseEntity.noContent().build();
    }

}
