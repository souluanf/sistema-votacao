package dev.luanfernandes.votacao.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import dev.luanfernandes.votacao.domain.entity.Associado;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;

@Data
@JsonInclude(value = Include.NON_NULL)
@ApiModel(value = "Associado")
public class AssociadoDTO {

	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	@NotBlank(message = "CPF deve ser informado")
	@CPF(message = "CPF é inválido")
	@JsonProperty(value = "CPF")
	private String cpf;
	@NotBlank(message = "Nome deve ser informado")
	private String nome;

	public static Associado toAssociado(AssociadoDTO associadoDTO) {
		return new ModelMapper().map(associadoDTO, Associado.class);
	}

	public static AssociadoDTO from(Associado associado) {
		return new ModelMapper().map(associado, AssociadoDTO.class);
	}
}