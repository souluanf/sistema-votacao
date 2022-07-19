package dev.luanfernandes.votacao.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import dev.luanfernandes.votacao.domain.entity.Sessao;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

@Data
@JsonInclude(value = Include.NON_NULL)
@ApiModel(value = "Sessao")
public class SessaoDTO {

	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	@NotNull(message = "Id da pauta deve ser informado")
	private Long idPauta;
	@NotBlank(message = "Descrição da pauta deve ser informado")
	private String descricao;
	@NotNull(message = "Data/Hora início deve ser informada")
	private OffsetDateTime dataHoraInicio;
	private OffsetDateTime dataHoraFechamento;

	public static Sessao toSessao(SessaoDTO sessaoDTO) {
		return new ModelMapper().map(sessaoDTO, Sessao.class);
	}

	public static SessaoDTO from(Sessao sessao) {
		return new ModelMapper().map(sessao, SessaoDTO.class);
	}
}