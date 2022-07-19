package dev.luanfernandes.votacao.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pauta implements Serializable {

	@Serial
	private static final long serialVersionUID = 5349085566205695546L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String nome;
	
	@OneToMany(mappedBy = "pauta")
	private List<Sessao> sessoes;
}