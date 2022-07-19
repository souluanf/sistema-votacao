package dev.luanfernandes.votacao.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import dev.luanfernandes.votacao.domain.entity.Pauta;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;

@Data
@JsonInclude(value = Include.NON_NULL)
@ApiModel(value = "Pauta")
public class PautaDTO {

	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	@NotBlank(message = "Nome deve ser informado")
	private String nome;

	public static Pauta toPauta(PautaDTO pautaDTO) {
		return new ModelMapper().map(pautaDTO, Pauta.class);
	}

	public static PautaDTO from(Pauta pauta) {
		return new ModelMapper().map(pauta, PautaDTO.class);
	}
}