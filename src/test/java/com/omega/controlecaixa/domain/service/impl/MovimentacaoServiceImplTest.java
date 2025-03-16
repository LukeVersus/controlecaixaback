package com.omega.controlecaixa.domain.service.impl;

import com.omega.controlecaixa.domain.model.Caixa;
import com.omega.controlecaixa.domain.model.Movimentacao;
import com.omega.controlecaixa.repositories.CaixaRepository;
import com.omega.controlecaixa.repositories.MovimentacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovimentacaoServiceImplTest {

    @Mock
    MovimentacaoRepository repository;

    @Mock
    CaixaRepository caixaRepository;

    @InjectMocks
    MovimentacaoServiceImpl service;

    Long caixaId;

    Long movimentacaoId;

    Integer ano;

    Integer mes;

    Movimentacao movimentacao;

    BigDecimal valor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        movimentacaoId = 1L;
        caixaId = 999L;
        ano = 2025;
        mes = 5;
        movimentacao = new Movimentacao();
        movimentacao.setId(movimentacaoId);
        movimentacao.setValor(BigDecimal.valueOf(100.0));
        valor = BigDecimal.valueOf(200.0);
    }

    @Test
    void movimentacoesPorCaixaException() {
        when(caixaRepository.findById(caixaId)).thenReturn(java.util.Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.movimentacoesPorCaixa(caixaId)
        );

        assertEquals("Caixa não encontrado.", exception.getMessage());

        verify(caixaRepository).findById(caixaId);
        verify(repository, never()).findByCaixaId(anyLong());
    }

    @Test
    void movimentacoesPorCaixaSuccess() {
        when(caixaRepository.findById(caixaId)).thenReturn(java.util.Optional.of(new Caixa()));
        when(repository.findByCaixaId(caixaId)).thenReturn(anyList());

        List<Movimentacao> result = service.movimentacoesPorCaixa(caixaId);

        assertNotNull(result);

        verify(caixaRepository).findById(caixaId);
        verify(repository).findByCaixaId(caixaId);
    }

    @Test
    void movimentacoesPorCaixaPorAnoException() {
        when(caixaRepository.findById(caixaId)).thenReturn(java.util.Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.movimentacoesPorCaixaPorAno(caixaId, ano)
        );

        assertEquals("Caixa não encontrado.", exception.getMessage());

        verify(caixaRepository).findById(caixaId);
        verify(repository, never()).findByCaixaIdAndAno(anyLong(), anyInt());
    }

    @Test
    void movimentacoesPorCaixaPorAno() {
        when(caixaRepository.findById(caixaId)).thenReturn(java.util.Optional.of(new Caixa()));
        when(repository.findByCaixaIdAndAno(caixaId, ano)).thenReturn(anyList());

        List<Movimentacao> result = service.movimentacoesPorCaixaPorAno(caixaId, ano);

        assertNotNull(result);

        verify(caixaRepository).findById(caixaId);
        verify(repository).findByCaixaIdAndAno(caixaId, ano);
    }

    @Test
    void movimentacoesPorCaixaPorMesException() {
        when(caixaRepository.findById(caixaId)).thenReturn(java.util.Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.movimentacoesPorCaixaPorMes(caixaId, ano, mes)
        );

        assertEquals("Caixa não encontrado.", exception.getMessage());

        verify(caixaRepository).findById(caixaId);
        verify(repository, never()).findByCaixaIdAndAnoAndMes(anyLong(), anyInt(), anyInt());
    }

    @Test
    void movimentacoesPorCaixaPorMes() {
        when(caixaRepository.findById(caixaId)).thenReturn(java.util.Optional.of(new Caixa()));
        when(repository.findByCaixaIdAndAnoAndMes(caixaId, ano, mes)).thenReturn(anyList());

        List<Movimentacao> result = service.movimentacoesPorCaixaPorMes(caixaId, ano, mes);

        assertNotNull(result);

        verify(caixaRepository).findById(caixaId);
        verify(repository).findByCaixaIdAndAnoAndMes(caixaId, ano, mes);
    }

    @Test
    void recuperarMovimentacaoException() {
        when(repository.findById(movimentacaoId)).thenReturn(java.util.Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.recuperarMovimentacao(movimentacaoId)
        );

        assertEquals("Movimentação não encontrada.", exception.getMessage());

        verify(repository).findById(movimentacaoId);
    }

    @Test
    void recuperarMovimentacao() {
        when(repository.findById(movimentacaoId)).thenReturn(java.util.Optional.of(movimentacao));

        Movimentacao result = service.recuperarMovimentacao(movimentacaoId);

        assertNotNull(result);
        assertEquals(movimentacaoId, result.getId());
        assertEquals(BigDecimal.valueOf(100.0), result.getValor());

        verify(repository).findById(movimentacaoId);
    }

    @Test
    void salvarMovimentacao() {
        when(repository.save(any(Movimentacao.class))).thenReturn(movimentacao);

        Movimentacao result = service.salvarMovimentacao(movimentacao);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(100.0), result.getValor());

        verify(repository).save(movimentacao);
    }

    @Test
    void alterarValorSuccess() {
        when(repository.findById(movimentacaoId)).thenReturn(java.util.Optional.of(movimentacao));
        when(repository.save(any(Movimentacao.class))).thenReturn(movimentacao);

        Movimentacao result = service.alterarValor(movimentacaoId, valor);

        assertNotNull(result);
        assertEquals(valor, result.getValor());

        verify(repository).findById(movimentacaoId);
        verify(repository).save(movimentacao);
    }

    @Test
    void alterarValorException() {
        when(repository.findById(movimentacaoId)).thenReturn(java.util.Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.alterarValor(movimentacaoId, valor)
        );

        assertEquals("Movimentação não encontrada.", exception.getMessage());

        verify(repository).findById(movimentacaoId);
        verify(repository, never()).save(any(Movimentacao.class));
    }

    @Test
    void alterarMovimentacao() {
        when(repository.findById(movimentacaoId)).thenReturn(java.util.Optional.of(movimentacao));
        Movimentacao novaMovimentacao = new Movimentacao();
        novaMovimentacao.setValor(BigDecimal.valueOf(150.0));

        when(repository.save(any(Movimentacao.class))).thenReturn(novaMovimentacao);

        service.alterarMovimentacao(movimentacaoId, novaMovimentacao);

        verify(repository).findById(movimentacaoId);
        verify(repository).save(novaMovimentacao);
    }

    @Test
    void alterarMovimentacaoException() {
        when(repository.findById(movimentacaoId)).thenReturn(java.util.Optional.empty());
        Movimentacao novaMovimentacao = new Movimentacao();
        novaMovimentacao.setValor(BigDecimal.valueOf(150.0));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.alterarMovimentacao(movimentacaoId, novaMovimentacao)
        );

        assertEquals("Movimentação não encontrada.", exception.getMessage());

        verify(repository).findById(movimentacaoId);
        verify(repository, never()).save(any(Movimentacao.class));
    }

    @Test
    void excluirMovimentacao() {
        when(repository.findById(movimentacaoId)).thenReturn(java.util.Optional.of(movimentacao));

        service.excluirMovimentacao(movimentacaoId);

        verify(repository).findById(movimentacaoId);
        verify(repository).deleteById(movimentacaoId);
    }

    @Test
    void excluirMovimentacaoException() {
        when(repository.findById(movimentacaoId)).thenReturn(java.util.Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.excluirMovimentacao(movimentacaoId)
        );

        assertEquals("Movimentação não encontrada.", exception.getMessage());

        verify(repository).findById(movimentacaoId);
        verify(repository, never()).deleteById(anyLong());
    }
}