package com.omega.controlecaixa.controllers;

import com.omega.controlecaixa.domain.model.Caixa;
import com.omega.controlecaixa.domain.service.interfaces.CaixaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
