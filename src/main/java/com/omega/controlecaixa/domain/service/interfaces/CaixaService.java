package com.omega.controlecaixa.domain.service.interfaces;

import com.omega.controlecaixa.domain.model.Caixa;

import java.math.BigDecimal;
import java.util.List;

public interface CaixaService {
    List<Caixa> caixasDisponiveis();
    Caixa recuperarCaixa(Long id);
    Caixa salvarCaixa(Caixa caixa);
    Caixa alterarSaldoInicial(Long id, BigDecimal saldoInicial);
    void alterarCaixa(Long id, Caixa caixa);
    void excluirCaixa(Long id);
}
