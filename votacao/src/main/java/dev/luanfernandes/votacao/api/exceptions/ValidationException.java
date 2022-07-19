package dev.luanfernandes.votacao.api.exceptions;

import lombok.Getter;

import java.io.Serial;

@Getter
public class ValidationException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 10148988671281605L;

	public ValidationException(String message) {
		super(message);
	}
}
