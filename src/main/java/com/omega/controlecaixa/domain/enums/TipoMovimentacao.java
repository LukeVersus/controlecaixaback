package com.omega.controlecaixa.domain.enums;

import lombok.Getter;

@Getter
public enum TipoMovimentacao {
    ENTRADA("E", "Entrada"),
    SAIDA("S", "Saída");

    private final String sigla;
    private final String descricao;

    TipoMovimentacao(String sigla, String descricao) {
        this.sigla = sigla;
        this.descricao = descricao;
    }
}
