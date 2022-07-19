package dev.luanfernandes.votacao.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sessao implements Serializable {

	@Serial
	private static final long serialVersionUID = -3689697810416000870L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "id_pauta")
	private Pauta pauta;
	private String descricao;
	private OffsetDateTime dataHoraInicio;
	private OffsetDateTime dataHoraFechamento;
	public boolean isAberta() {
		OffsetDateTime now = OffsetDateTime.now();
		return now.compareTo(this.dataHoraInicio) >= 0 && now.compareTo(this.dataHoraFechamento) <= 0;
	}
}