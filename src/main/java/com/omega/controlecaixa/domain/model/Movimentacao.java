package com.omega.controlecaixa.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "movimentacao", schema = "omega")
@Data
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_movimentacao")
    @SequenceGenerator(name = "seq_movimentacao", sequenceName = "seq_movimentacao", schema = "omega", allocationSize = 1)
    private Long id;

    private LocalDate data;

    private String tipo;

    @ManyToOne
    @JoinColumn(name = "caixa_id")
    private Caixa caixa;

    private String descricao;

    private BigDecimal valor = BigDecimal.ZERO;
}
