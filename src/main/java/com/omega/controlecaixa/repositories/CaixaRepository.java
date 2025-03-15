package com.omega.controlecaixa.repositories;

import com.omega.controlecaixa.domain.model.Caixa;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaixaRepository extends JpaRepository<Caixa, Long> {

    Optional<Object> findByDescricao(@NotEmpty(message = "Descrição é obrigatória.") String descricao);
}
