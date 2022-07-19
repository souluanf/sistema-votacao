package dev.luanfernandes.votacao.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@JsonInclude(value = Include.NON_NULL)
@ApiModel(value = "Voto")
public class VotoDTO {

	@NotNull(message = "Id da sessão deve ser informada")
	private Long idSessao;
	@NotBlank(message = "CPF deve ser informado")
	@CPF(message = "CPF é inválido")
	@JsonProperty(value = "cpf")
	private String cpf;
	@NotNull(message = "Valor deve ser informado")
	private Boolean valor;
}