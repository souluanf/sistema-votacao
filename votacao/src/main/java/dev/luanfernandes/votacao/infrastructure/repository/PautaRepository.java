package dev.luanfernandes.votacao.infrastructure.repository;


import dev.luanfernandes.votacao.domain.entity.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {
    Optional<Pauta> findByNomeIgnoreCase(String nome);
}
