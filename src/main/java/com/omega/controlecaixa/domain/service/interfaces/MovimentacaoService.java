package com.omega.controlecaixa.domain.service.interfaces;

import com.omega.controlecaixa.domain.model.Movimentacao;

import java.math.BigDecimal;
import java.util.List;

public interface MovimentacaoService {
    List<Movimentacao> movimentacoesPorCaixa(Long idCaixa);
    List<Movimentacao> movimentacoesPorCaixaPorAno(Long idCaixa, Integer ano);
    List<Movimentacao> movimentacoesPorCaixaPorMes(Long idCaixa, Integer ano, Integer mes);
    Movimentacao recuperarMovimentacao(Long id);
    Movimentacao salvarMovimentacao(Movimentacao movimentacao);
    Movimentacao alterarValor(Long id, BigDecimal valor);
    void alterarMovimentacao(Long id, Movimentacao movimentacao);
    void excluirMovimentacao(Long id);
}
