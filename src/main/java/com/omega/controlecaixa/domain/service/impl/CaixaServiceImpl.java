package com.omega.controlecaixa.domain.service.impl;

import com.omega.controlecaixa.domain.model.Caixa;
import com.omega.controlecaixa.domain.service.interfaces.CaixaService;
import com.omega.controlecaixa.repositories.CaixaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CaixaServiceImpl implements CaixaService {

    @Autowired
    CaixaRepository repository;

    @Override
    public List<Caixa> caixasDisponiveis() {
        return repository.findAll();
    }

    @Override
    public Caixa recuperarCaixa(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Caixa não encontrado")
        );
    }

    @Override
    public Caixa salvarCaixa(Caixa caixa) {
        repository.findByDescricao(caixa.getDescricao()).ifPresent(
                c -> {
                    throw new RuntimeException("Caixa já cadastrado");
                }
        );
        return repository.save(caixa);
    }

    @Override
    @Transactional
    public Caixa alterarSaldoInicial(Long id, BigDecimal saldoInicial) {
        Caixa caixa = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Caixa não encontrado")
        );
        caixa.setSaldoInicial(saldoInicial);
        return repository.save(caixa);
    }

    @Override
    @Transactional
    public void alterarCaixa(Long id, Caixa caixa) {
        repository.findById(id).orElseThrow(
                () -> new RuntimeException("Caixa não encontrado")
        );
        caixa.setId(id);
        repository.save(caixa);
    }

    @Override
    @Transactional
    public void excluirCaixa(Long id) {
        Caixa caixa = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Caixa não encontrado")
        );
        repository.delete(caixa);
    }
}
