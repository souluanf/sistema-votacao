package dev.luanfernandes.votacao.api.exceptions;

import java.io.Serial;

public class NotDeleteException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -3148957994625893570L;

	public NotDeleteException(String message) {
		super(message);
	}

}