package com.omega.controlecaixa.repositories;

import com.omega.controlecaixa.domain.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository  extends JpaRepository<Movimentacao, Long> {

}
