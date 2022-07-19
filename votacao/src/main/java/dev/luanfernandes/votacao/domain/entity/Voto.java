package dev.luanfernandes.votacao.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Voto implements Serializable {

	@Serial
	private static final long serialVersionUID = 1831231049985027401L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_sessao")
	private Sessao sessao;

	@ManyToOne
	@JoinColumn(name = "id_associado")
	private Associado associado;

	@Column(name = "valor")
	private Boolean valor;
}