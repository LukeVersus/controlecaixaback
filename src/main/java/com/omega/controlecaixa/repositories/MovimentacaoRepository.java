package com.omega.controlecaixa.repositories;

import com.omega.controlecaixa.domain.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoRepository  extends JpaRepository<Movimentacao, Long> {

    List<Movimentacao> findByCaixaId(Long idCaixa);

    @Query(value = "SELECT *\n" +
            "FROM omega.movimentacao\n" +
            "WHERE EXTRACT(YEAR FROM movimentacao.\"data\") = ?2\n" +
            "  and caixa_id = ?1", nativeQuery = true)
    List<Movimentacao> findByCaixaIdAndAno(Long idCaixa, Integer ano);

    @Query(value = "SELECT *\n" +
            "FROM omega.movimentacao\n" +
            "WHERE EXTRACT(YEAR FROM movimentacao.\"data\") = ?2\n" +
            "  AND EXTRACT(MONTH FROM movimentacao.\"data\") = ?3\n" +
            "  and caixa_id = ?1", nativeQuery = true)
    List<Movimentacao> findByCaixaIdAndAnoAndMes(Long idCaixa, Integer ano, Integer mes);
}
