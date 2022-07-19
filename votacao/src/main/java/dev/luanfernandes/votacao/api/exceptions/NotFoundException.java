package dev.luanfernandes.votacao.api.exceptions;

import lombok.Getter;

import java.io.Serial;

@Getter
public class NotFoundException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -1928721796257705536L;

	public NotFoundException(String message) {
		super(message);
	}
}
