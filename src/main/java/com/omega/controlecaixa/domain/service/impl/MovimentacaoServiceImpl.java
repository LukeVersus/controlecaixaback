package com.omega.controlecaixa.domain.service.impl;

import com.omega.controlecaixa.domain.model.Movimentacao;
import com.omega.controlecaixa.domain.service.interfaces.MovimentacaoService;
import com.omega.controlecaixa.repositories.CaixaRepository;
import com.omega.controlecaixa.repositories.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimentacaoServiceImpl implements MovimentacaoService {

    @Autowired
    MovimentacaoRepository repository;

    @Autowired
    CaixaRepository caixaRepository;

    @Override
    public List<Movimentacao> movimentacoesPorCaixa(Long idCaixa) {
        caixaRepository.findById(idCaixa).orElseThrow(
                () -> new RuntimeException("Caixa não encontrado.")
        );
        return repository.findByCaixaId(idCaixa);
    }

    @Override
    public List<Movimentacao> movimentacoesPorCaixaPorAno(Long idCaixa, Integer ano) {
        caixaRepository.findById(idCaixa).orElseThrow(
                () -> new RuntimeException("Caixa não encontrado.")
        );
        return repository.findByCaixaIdAndAno(idCaixa, ano);
    }

    @Override
    public List<Movimentacao> movimentacoesPorCaixaPorMes(Long idCaixa, Integer ano, Integer mes) {
        caixaRepository.findById(idCaixa).orElseThrow(
                () -> new RuntimeException("Caixa não encontrado.")
        );
        return repository.findByCaixaIdAndAnoAndMes(idCaixa,ano,mes);
    }

    @Override
    public Movimentacao recuperarMovimentacao(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Movimentação não encontrada.")
        );
    }

    @Override
    public Movimentacao salvarMovimentacao(Movimentacao movimentacao) {
        return repository.save(movimentacao);
    }

    @Override
    public void alterarMovimentacao(Long id, Movimentacao movimentacao) {
        repository.findById(id).orElseThrow(
                () -> new RuntimeException("Movimentação não encontrada.")
        );
        movimentacao.setId(id);
        repository.save(movimentacao);
    }

    @Override
    public void excluirMovimentacao(Long id) {
        repository.findById(id).orElseThrow(
                () -> new RuntimeException("Movimentação não encontrada.")
        );
        repository.deleteById(id);
    }
}
