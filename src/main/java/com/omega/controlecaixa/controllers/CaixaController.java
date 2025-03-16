package com.omega.controlecaixa.controllers;

import com.omega.controlecaixa.domain.model.Caixa;
import com.omega.controlecaixa.domain.service.interfaces.CaixaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/caixa")
public class CaixaController {

    @Autowired
    CaixaService service;

    @GetMapping
    ResponseEntity<List<Caixa>> caixasDisponiveis() {
        return ResponseEntity.ok().body(service.caixasDisponiveis());
    }

    @GetMapping("{id}")
    ResponseEntity<Caixa> recuperarCaixa(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(service.recuperarCaixa(id));
    }

    @PostMapping
    ResponseEntity<Caixa> salvarCaixa(@RequestBody Caixa caixa) {
        return ResponseEntity.ok().body(service.salvarCaixa(caixa));
    }

    @PutMapping("{id}/saldo-inicial")
    ResponseEntity<Caixa> alterarSaldoInicial(@PathVariable("id") Long id, @RequestBody BigDecimal saldoInicial) {
        return ResponseEntity.ok().body(service.alterarSaldoInicial(id, saldoInicial));
    }

    @PutMapping("{id}")
    ResponseEntity<String> alterarCaixa(@PathVariable("id") Long id, @RequestBody Caixa caixa) {
        service.alterarCaixa(id, caixa);
        return ResponseEntity.ok().body("Alterado com sucesso.");
    }

    @DeleteMapping("{id}")
    ResponseEntity<String> excluirCaixa(@PathVariable("id") Long id) {
        service.excluirCaixa(id);
        return ResponseEntity.ok().body("Excluído com sucesso.");
    }

}
