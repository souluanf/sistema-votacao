package dev.luanfernandes.votacao.domain.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "CPF",description = "Representa retorno da consulta")
public class UserInfoDto {

    @ApiModelProperty(example = "ABLE_TO_VOTE")
    String status;
}
