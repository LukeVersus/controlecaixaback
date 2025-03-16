package com.omega.controlecaixa.controllers;

import com.omega.controlecaixa.domain.model.Movimentacao;
import com.omega.controlecaixa.domain.service.interfaces.MovimentacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovimentacaoControllerTest {

    @Mock
    MovimentacaoService service;

    @InjectMocks
    MovimentacaoController controller;

    Long idCaixa;

    Long idMovimentacao;

    Movimentacao movimentacao;

    Integer ano;

    Integer mes;

    List<Movimentacao> movimentacoes;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        idCaixa = 1L;
        idMovimentacao = 1L;
        movimentacao = new Movimentacao();
        ano = 2025;
        mes = 6;
        movimentacoes = List.of(new Movimentacao());
        movimentacao.setId(idMovimentacao);
    }

    @Test
    void movimentacoesPorCaixaSuccess() {
        when(service.movimentacoesPorCaixa(idCaixa)).thenReturn(anyList());

        MovimentacaoController controller = new MovimentacaoController();
        ReflectionTestUtils.setField(controller, "service", service);

        ResponseEntity<List<Movimentacao>> response = controller.movimentacoesPorCaixa(idCaixa);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(service).movimentacoesPorCaixa(idCaixa);
    }

    @Test
    void recuperarMovimentacaoException() {
        when(service.recuperarMovimentacao(idCaixa)).thenThrow(new RuntimeException("Movimentação não encontrada"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            controller.recuperarMovimentacao(idCaixa);
        });

        verify(service, times(1)).recuperarMovimentacao(idCaixa);
        assertEquals("Movimentação não encontrada", exception.getMessage());
    }

    @Test
    void movimentacoesPorCaixaPorAno() {
        when(service.movimentacoesPorCaixaPorAno(idCaixa, ano)).thenReturn(movimentacoes);

        ResponseEntity<List<Movimentacao>> response = controller.movimentacoesPorCaixaPorAno(idCaixa, ano);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(movimentacoes, response.getBody());
        verify(service).movimentacoesPorCaixaPorAno(idCaixa, ano);
    }

    @Test
    void movimentacoesPorCaixaPorMes() {
        when(service.movimentacoesPorCaixaPorMes(idCaixa, ano, mes)).thenReturn(movimentacoes);

        ResponseEntity<List<Movimentacao>> response = controller.movimentacoesPorCaixaPorMes(idCaixa, ano, mes);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(movimentacoes, response.getBody());
        verify(service).movimentacoesPorCaixaPorMes(idCaixa, ano, mes);
    }

    @Test
    void recuperarMovimentacao() {
        when(service.recuperarMovimentacao(idMovimentacao)).thenReturn(movimentacao);

        ResponseEntity<Movimentacao> response = controller.recuperarMovimentacao(idMovimentacao);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertSame(movimentacao, response.getBody());
        verify(service).recuperarMovimentacao(idMovimentacao);
    }

    @Test
    void salvarMovimentacao() {
        when(service.salvarMovimentacao(any(Movimentacao.class))).thenReturn(movimentacao);

        ResponseEntity<Movimentacao> response = controller.salvarMovimentacao(movimentacao);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertSame(movimentacao, response.getBody());
        verify(service).salvarMovimentacao(movimentacao);
    }

    @Test
    void alterarSaldoInicial() {
        BigDecimal novoSaldo = new BigDecimal("100.00");
        when(service.alterarValor(idMovimentacao, novoSaldo)).thenReturn(movimentacao);

        ResponseEntity<Movimentacao> response = controller.alterarSaldoInicial(idMovimentacao, novoSaldo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertSame(movimentacao, response.getBody());
        verify(service).alterarValor(idMovimentacao, novoSaldo);
    }

    @Test
    void alterarMovimentacao() {
        doNothing().when(service).alterarMovimentacao(idMovimentacao, movimentacao);

        ResponseEntity<String> response = controller.alterarMovimentacao(idMovimentacao, movimentacao);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Alterado com sucesso.", response.getBody());
        verify(service).alterarMovimentacao(idMovimentacao, movimentacao);

        verifyNoMoreInteractions(service);
    }

    @Test
    void alterarMovimentacaoException() {
        doThrow(new RuntimeException("Movimentação não encontrada"))
                .when(service).alterarMovimentacao(idMovimentacao, movimentacao);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            controller.alterarMovimentacao(idMovimentacao, movimentacao);
        });

        verify(service, times(1)).alterarMovimentacao(idMovimentacao, movimentacao);
        assertEquals("Movimentação não encontrada", exception.getMessage());
    }

    @Test
    void excluirMovimentacao() {
        doNothing().when(service).excluirMovimentacao(idMovimentacao);

        ResponseEntity<String> response = controller.excluirMovimentacao(idMovimentacao);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Excluído com sucesso.", response.getBody());
        verify(service).excluirMovimentacao(idMovimentacao);
    }

    @Test
    void excluirMovimentacaoException() {
        doThrow(new RuntimeException("Movimentação não encontrada"))
                .when(service).excluirMovimentacao(idMovimentacao);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            controller.excluirMovimentacao(idMovimentacao);
        });

        verify(service, times(1)).excluirMovimentacao(idMovimentacao);
        assertEquals("Movimentação não encontrada", exception.getMessage());
    }
}