package com.github.caioleria.ms.pagamentos.service;

import com.github.caioleria.ms.pagamentos.client.PedidoClient;
import com.github.caioleria.ms.pagamentos.dto.PagamentoDto;
import com.github.caioleria.ms.pagamentos.entities.Pagamentos;
import com.github.caioleria.ms.pagamentos.entities.Status;
import com.github.caioleria.ms.pagamentos.exceptions.PagamentoAprovadoException;
import com.github.caioleria.ms.pagamentos.exceptions.ResourceNotFoundException;
import com.github.caioleria.ms.pagamentos.repositories.PagamentoRepository;

import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoClient pedidoClient;

    @Transactional
    public PagamentoDto confirmarPagamentoDoPedido (Long id){
Pagamentos pagamentos = pagamentoRepository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("Pagamento não encontrado . id: "+id)
);
pagamentos.setStatus(Status.APROVADO);
pagamentoRepository.save(pagamentos);
//try{
//    pedidoClient.confirmarPagamento(pagamentos.getPedidoId());
//} catch (FeignException.NotFound e){
 //   throw new ResourceNotFoundException("Pedido não encontrado, id: " + pagamentos.getPedidoId());
//} catch (FeignException e) {
 //   throw new RuntimeException("falha ao comunicar com ms-pedido");
//}
return new PagamentoDto(pagamentos);
    }

    @Transactional(readOnly = true)
    public List<PagamentoDto> findAllPagamentos(){
        List<Pagamentos> pagamentos= pagamentoRepository.findAll();
        return pagamentos.stream().map(PagamentoDto::new).toList();
    }
    @Transactional(readOnly = true)
    public PagamentoDto findById(Long id){
        Pagamentos pagamento =pagamentoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("pagamento "+ id+ "não foi feito")
        );
        return new PagamentoDto(pagamento);
    }

    @Transactional
    public PagamentoDto savePagamento (PagamentoDto pagamentoDto){
        Pagamentos pagamento = new Pagamentos();
        mapperToPagamentoDto(pagamentoDto, pagamento);
        pagamento.setStatus(Status.CRIADO);
        pagamento = pagamentoRepository.save(pagamento);
        return new PagamentoDto(pagamento);
    }
    @Transactional
    public PagamentoDto updatePagamento (Long id, PagamentoDto pagamentoDto){
        try{
            Pagamentos pagamento = pagamentoRepository.getReferenceById(id);
            if(pagamento.getStatus().equals(Status.APROVADO)){
                throw new PagamentoAprovadoException(
                        String.format("Pagamento id %d ja esta aprovado e não pode ser alterado",id)
                );
            }
            mapperToPagamentoDto(pagamentoDto, pagamento);
            pagamento = pagamentoRepository.save(pagamento);
            return new PagamentoDto(pagamento);
        } catch(EntityNotFoundException e){
            throw new ResourceNotFoundException("Pagamento "+ id+" não foi achado");
        }
    }

    @Transactional
    public void deletePagamento (Long id){
        if (!pagamentoRepository.existsById(id)){
            throw new ResourceNotFoundException("O pagamento não existe");
        }else {

            pagamentoRepository.deleteById(id);
        }
    }
    @Transactional
    public PagamentoDto alterarStatusDoPagamento(Long id){
        Pagamentos pagamento = pagamentoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("pagamnto não encontrado || id: "+ id)
        );
        pagamento.setStatus(Status.CONFIRMACAO_PENDENTE);
        pagamento = pagamentoRepository.save(pagamento);
        return new PagamentoDto(pagamento);
    }

    private void mapperToPagamentoDto (PagamentoDto pagamentoDto,Pagamentos pagamento){
        pagamento.setNome(pagamentoDto.getNome());
        pagamento.setValidade(pagamentoDto.getValidade());
        pagamento.setValor(pagamentoDto.getValor());
        pagamento.setCodigoSeguranca(pagamentoDto.getCodigoSeguranca());
        pagamento.setPedidoId(pagamentoDto.getPedidoId());
        pagamento.setNumeroCartao(pagamentoDto.getNumeroCartao());
    }

}
