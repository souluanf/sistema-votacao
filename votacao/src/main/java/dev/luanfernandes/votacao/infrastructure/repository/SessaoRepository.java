package dev.luanfernandes.votacao.infrastructure.repository;


import dev.luanfernandes.votacao.domain.entity.Pauta;
import dev.luanfernandes.votacao.domain.entity.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    boolean findByPauta(Pauta pauta);
    @Query("SELECT SUM(CASE WHEN v.valor = TRUE THEN 1 ELSE 0 END) AS totalSim,SUM(CASE WHEN v.valor = FALSE THEN 1 ELSE 0 END) AS totalNao FROM Voto v WHERE v.sessao.id = :idSessao")
    List<Object[]> obterResultadoPorIdSessao(@Param("idSessao") Long idSessao);
}