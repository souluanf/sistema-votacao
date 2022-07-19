package dev.luanfernandes.votacao.api.exceptions.handler;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Getter
@AllArgsConstructor
@ToString
@ApiModel(value = "Problema")
public class StandardError implements Serializable {

    @Serial
    private static final long serialVersionUID = 5363719414519016637L;

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}