package com.omega.controlecaixa.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "caixa", schema = "omega")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Caixa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_caixa")
    @SequenceGenerator(name = "seq_caixa", sequenceName = "seq_caixa", schema = "omega", allocationSize = 1)
    private Long id;

    @NotEmpty(message = "Descrição é obrigatória.")
    private String descricao;

    private BigDecimal saldoInicial = BigDecimal.ZERO;
}
