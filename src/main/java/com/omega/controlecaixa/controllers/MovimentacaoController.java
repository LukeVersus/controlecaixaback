package com.omega.controlecaixa.controllers;

import com.omega.controlecaixa.domain.model.Movimentacao;
import com.omega.controlecaixa.domain.service.interfaces.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/movimentacao")
public class MovimentacaoController {

    @Autowired
    MovimentacaoService service;

    @GetMapping("/caixa/{idCaixa}")
    public ResponseEntity<List<Movimentacao>> movimentacoesPorCaixa(@PathVariable Long idCaixa) {
        return ResponseEntity.ok().body(service.movimentacoesPorCaixa(idCaixa));
    }

    @GetMapping("/caixa/{idCaixa}/ano/{ano}")
    public ResponseEntity<List<Movimentacao>> movimentacoesPorCaixaPorAno(
            @PathVariable Long idCaixa,
            @PathVariable Integer ano) {
        return ResponseEntity.ok().body(service.movimentacoesPorCaixaPorAno(idCaixa, ano));
    }

    @GetMapping("/caixa/{idCaixa}/ano/{ano}/mes/{mes}")
    public ResponseEntity<List<Movimentacao>> movimentacoesPorCaixaPorMes(
            @PathVariable Long idCaixa,
            @PathVariable Integer ano,
            @PathVariable Integer mes) {
        return ResponseEntity.ok().body(service.movimentacoesPorCaixaPorMes(idCaixa, ano, mes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimentacao> recuperarMovimentacao(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.recuperarMovimentacao(id));
    }

    @PostMapping
    public ResponseEntity<Movimentacao> salvarMovimentacao(@RequestBody Movimentacao movimentacao) {
        return ResponseEntity.ok().body(service.salvarMovimentacao(movimentacao));
    }

    @PutMapping("{id}/saldo-inicial")
    ResponseEntity<Movimentacao> alterarSaldoInicial(@PathVariable("id") Long id, @RequestBody BigDecimal valor) {
        return ResponseEntity.ok().body(service.alterarValor(id, valor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> alterarMovimentacao(
            @PathVariable Long id,
            @RequestBody Movimentacao movimentacao) {
        service.alterarMovimentacao(id, movimentacao);
        return ResponseEntity.ok().body("Alterado com sucesso.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirMovimentacao(@PathVariable Long id) {
        service.excluirMovimentacao(id);
        return ResponseEntity.ok().body("Excluído com sucesso.");
    }
}
