package dev.luanfernandes.votacao.domain.entity;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
public class ResultadoSessao {
	private Long idSessao;
	private Boolean aberta;
	private Integer totalSim;
	private Integer totalNao;
}