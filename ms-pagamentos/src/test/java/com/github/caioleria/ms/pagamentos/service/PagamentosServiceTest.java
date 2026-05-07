package com.github.caioleria.ms.pagamentos.service;

import com.github.caioleria.ms.pagamentos.exceptions.ResourceNotFoundException;
import com.github.caioleria.ms.pagamentos.repositories.PagamentoRepository;
import org.h2.message.Trace;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PagamentosServiceTest {
    @Mock
    private PagamentoRepository pagamentoRepository;

    @InjectMocks
    private PagamentoService pagamentoService;

    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp(){
        existingId = 1L;
        nonExistingId = Long.MAX_VALUE;
    }
    @Test
    void deletePagamentoByIdShouldDeleteWithIdExist(){
        Mockito.when(pagamentoRepository.existsById(existingId)).thenReturn(true);
        pagamentoService.deletePagamento(existingId);
        Mockito.verify(pagamentoRepository).existsById(existingId);
        Mockito.verify(pagamentoRepository, Mockito.times(1)).deleteById(existingId);
    }

    @Test
    @DisplayName("deletePagamentoById deveria lançar ResourceNotFoundExcepition quando o id não existe")
    void deletePagamentoByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){

        Mockito.when(pagamentoRepository.existsById(nonExistingId)).thenReturn(false);
        Assertions.assertThrows(ResourceNotFoundException.class,
                ()-> {
            pagamentoService.deletePagamento(nonExistingId);
                });
        Mockito.verify(pagamentoRepository).existsById(nonExistingId);
        Mockito.verify(pagamentoRepository, Mockito.never()).deleteById(Mockito.anyLong());
    }
}
