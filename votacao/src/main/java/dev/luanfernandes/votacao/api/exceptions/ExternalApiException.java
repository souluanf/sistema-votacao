package dev.luanfernandes.votacao.api.exceptions;

import java.io.Serial;

public class ExternalApiException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -2211743433439160771L;

    public ExternalApiException(String message) {
        super(message);
    }
}
