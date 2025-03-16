package com.omega.controlecaixa.controllers;

import com.omega.controlecaixa.domain.model.Caixa;
import com.omega.controlecaixa.domain.service.interfaces.CaixaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CaixaControllerTest {

    @Mock
    CaixaService service;

    @InjectMocks
    CaixaController controller;

    List<Caixa> caixas;

    Caixa caixa;

    Long id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        caixas = List.of(new Caixa(), new Caixa());
        caixa = new Caixa();
        id = 1L;
    }

    @Test
    void caixasDisponiveis() {
        when(service.caixasDisponiveis()).thenReturn(caixas);

        ResponseEntity<List<Caixa>> response = controller.caixasDisponiveis();

        verify(service, times(1)).caixasDisponiveis();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(caixas, response.getBody());
    }

    @Test
    void recuperarCaixa() {
        when(service.recuperarCaixa(id)).thenReturn(caixa);

        ResponseEntity<Caixa> response = controller.recuperarCaixa(id);

        verify(service, times(1)).recuperarCaixa(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(caixa, response.getBody());
    }

    @Test
    void recuperarCaixaException() {
        when(service.recuperarCaixa(id)).thenThrow(new RuntimeException("Caixa não encontrada"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            controller.recuperarCaixa(id);
        });

        verify(service, times(1)).recuperarCaixa(id);
        assertEquals("Caixa não encontrada", exception.getMessage());
    }

    @Test
    void salvarCaixa() {
        when(service.salvarCaixa(caixa)).thenReturn(caixa);

        ResponseEntity<Caixa> response = controller.salvarCaixa(caixa);

        verify(service, times(1)).salvarCaixa(caixa);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(caixa, response.getBody());
    }

    @Test
    void alterarSaldoInicial() {
        BigDecimal saldoInicial = new BigDecimal("100.00");
        when(service.alterarSaldoInicial(id, saldoInicial)).thenReturn(caixa);

        ResponseEntity<Caixa> response = controller.alterarSaldoInicial(id, saldoInicial);

        verify(service, times(1)).alterarSaldoInicial(id, saldoInicial);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(caixa, response.getBody());
    }

    @Test
    void alterarCaixa() {
        doNothing().when(service).alterarCaixa(id, caixa);

        ResponseEntity<String> response = controller.alterarCaixa(id, caixa);

        verify(service, times(1)).alterarCaixa(id, caixa);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Alterado com sucesso.", response.getBody());
    }

    @Test
    void alterarCaixaException() {
        doThrow(new RuntimeException("Erro ao alterar caixa")).when(service).alterarCaixa(id, caixa);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            controller.alterarCaixa(id, caixa);
        });

        verify(service, times(1)).alterarCaixa(id, caixa);
        assertEquals("Erro ao alterar caixa", exception.getMessage());
    }

    @Test
    void excluirCaixa() {
        doNothing().when(service).excluirCaixa(id);

        ResponseEntity<String> response = controller.excluirCaixa(id);

        verify(service, times(1)).excluirCaixa(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Excluído com sucesso.", response.getBody());
    }

    @Test
    void excluirCaixaException() {
        doThrow(new RuntimeException("Caixa não encontrada")).when(service).excluirCaixa(id);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            controller.excluirCaixa(id);
        });

        verify(service, times(1)).excluirCaixa(id);
        assertEquals("Caixa não encontrada", exception.getMessage());
    }
}