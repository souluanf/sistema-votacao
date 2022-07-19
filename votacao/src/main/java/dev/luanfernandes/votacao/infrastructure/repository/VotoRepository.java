package dev.luanfernandes.votacao.infrastructure.repository;


import dev.luanfernandes.votacao.domain.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

	@Query(value = "SELECT COALESCE(COUNT(v),0) > 0 FROM Voto v WHERE v.sessao.id = :sessaoId AND v.associado.id = :associadoId")
	boolean existsVotoByIdSessaoAndIdAssociado(@Param("sessaoId") Long sessaoId, @Param("associadoId") Long associadoId);
}