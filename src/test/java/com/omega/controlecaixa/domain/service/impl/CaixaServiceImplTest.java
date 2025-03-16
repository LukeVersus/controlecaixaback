package com.omega.controlecaixa.domain.service.impl;

import com.omega.controlecaixa.domain.model.Caixa;
import com.omega.controlecaixa.repositories.CaixaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CaixaServiceImplTest {

    @Mock
    private CaixaRepository repository;

    @InjectMocks
    private CaixaServiceImpl caixaService;

    List<Caixa> caixas;

    Caixa caixa;

    Long id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        caixa = new Caixa(1L, "Caixa 1", BigDecimal.valueOf(100));
        caixas = List.of(
                new Caixa(1L, "Caixa 1", BigDecimal.valueOf(100)),
                new Caixa(2L, "Caixa 2", BigDecimal.valueOf(200))
        );
        id = 999L;
    }

    @Test
    void caixasDisponiveis() {
        when(repository.findAll()).thenReturn(caixas);

        List<Caixa> result = caixaService.caixasDisponiveis();

        assertEquals(caixas, result);
        verify(repository, times(1)).findAll();
    }

    @Test
    void recuperarCaixaException() {
        when(repository.findById(id)).thenReturn(java.util.Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                caixaService.recuperarCaixa(id)
        );

        assertEquals("Caixa não encontrado", exception.getMessage());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void recuperarCaixaSuccess() {
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(caixa));

        Caixa result = caixaService.recuperarCaixa(1L);

        assertEquals(caixa, result);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void salvarCaixaSuccess() {
        caixa = new Caixa(3L, "Caixa Novo", BigDecimal.valueOf(300));

        when(repository.findByDescricao("Caixa Novo")).thenReturn(java.util.Optional.empty());

        when(repository.save(caixa)).thenReturn(caixa);

        Caixa result = caixaService.salvarCaixa(caixa);

        assertEquals(caixa, result);

        verify(repository, times(1)).findByDescricao("Caixa Novo");
        verify(repository, times(1)).save(caixa);
    }

    @Test
    void salvarCaixaException() {
        when(repository.findByDescricao("Caixa 1")).thenReturn(java.util.Optional.of(caixa));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                caixaService.salvarCaixa(caixa)
        );

        assertEquals("Caixa já cadastrado", exception.getMessage());
        verify(repository, times(1)).findByDescricao("Caixa 1");
        verify(repository, never()).save(any());
    }

    @Test
    void alterarSaldoInicialException() {
        when(repository.findById(id)).thenReturn(java.util.Optional.empty());

        BigDecimal novoSaldo = BigDecimal.valueOf(500);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                caixaService.alterarSaldoInicial(id, novoSaldo)
        );

        assertEquals("Caixa não encontrado", exception.getMessage());
        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void alterarSaldoInicialSuccess() {
        BigDecimal novoSaldo = BigDecimal.valueOf(500);

        when(repository.findById(id)).thenReturn(java.util.Optional.of(caixa));
        when(repository.save(caixa)).thenReturn(caixa);

        Caixa result = caixaService.alterarSaldoInicial(id, novoSaldo);

        assertEquals(novoSaldo, result.getSaldoInicial());
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(caixa);
    }

    @Test
    void alterarCaixaException() {
        when(repository.findById(id)).thenReturn(java.util.Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                caixaService.alterarCaixa(id, caixa)
        );

        assertEquals("Caixa não encontrado", exception.getMessage());
        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any());
    }

    @Test
    void alterarCaixaSuccess() {
        Caixa caixaNovo = new Caixa(null, "Caixa Atualizado", BigDecimal.valueOf(500));

        when(repository.findById(1L)).thenReturn(java.util.Optional.of(caixa));
        when(repository.save(any(Caixa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        caixaService.alterarCaixa(1L, caixaNovo);

        verify(repository, times(1)).findById(1L);

        ArgumentCaptor<Caixa> caixaCaptor = ArgumentCaptor.forClass(Caixa.class);
        verify(repository, times(1)).save(caixaCaptor.capture());

        Caixa savedCaixa = caixaCaptor.getValue();
        assertEquals(1L, savedCaixa.getId());
        assertEquals("Caixa Atualizado", savedCaixa.getDescricao());
        assertEquals(BigDecimal.valueOf(500), savedCaixa.getSaldoInicial());
    }

    @Test
    void excluirCaixaException() {
        when(repository.findById(id)).thenReturn(java.util.Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                caixaService.excluirCaixa(id)
        );

        assertEquals("Caixa não encontrado", exception.getMessage());
        verify(repository, times(1)).findById(id);
        verify(repository, never()).delete(any());
    }

    @Test
    void excluirCaixaSuccess() {
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(caixa));

        caixaService.excluirCaixa(1L);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(caixa);
    }
}