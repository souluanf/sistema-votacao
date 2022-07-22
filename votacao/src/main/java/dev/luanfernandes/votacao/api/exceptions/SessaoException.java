package dev.luanfernandes.votacao.api.exceptions;

import java.io.Serial;

public class SessaoException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -3213742171543245664L;

	public SessaoException(String message) {
		super(message);
	}
}