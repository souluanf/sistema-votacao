package dev.luanfernandes.votacao.api.exceptions;

import java.io.Serial;

public class ConflictException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -4714735047018947536L;

	public ConflictException(String message) {
		super(message);
	}
}