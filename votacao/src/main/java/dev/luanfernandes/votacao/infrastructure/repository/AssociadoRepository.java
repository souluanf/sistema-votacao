package dev.luanfernandes.votacao.infrastructure.repository;


import dev.luanfernandes.votacao.domain.entity.Associado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {
	Optional<Associado> findByCpf(String cpf);
}