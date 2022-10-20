package org.selbowgreaser.request.exceptions;

public class CommandLineUserRequestException extends RuntimeException {
    public CommandLineUserRequestException() {
    }

    ;

    public CommandLineUserRequestException(String message) {
        super(message);
    }

    public CommandLineUserRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandLineUserRequestException(Throwable cause) {
        super(cause);
    }
}
