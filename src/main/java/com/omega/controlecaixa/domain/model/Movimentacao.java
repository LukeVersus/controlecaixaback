package com.omega.controlecaixa.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "movimentacao", schema = "omega")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movimentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_movimentacao")
    @SequenceGenerator(name = "seq_movimentacao", sequenceName = "seq_movimentacao", schema = "omega", allocationSize = 1)
    private Long id;

    @NotNull(message = "Data é obrigatória.")
    private LocalDate data;

    @NotEmpty(message = "Tipo é obrigatório.")
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "caixa_id")
    @NotNull(message = "Caixa é obrigatório.")
    private Caixa caixa;

    @NotEmpty(message = "Descrição é obrigatória.")
    private String descricao;

    private BigDecimal valor = BigDecimal.ZERO;
}
