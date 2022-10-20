package org.selbowgreaser.request.exceptions;

public class BotUserRequestException extends RuntimeException {
    public BotUserRequestException() {
    }

    ;

    public BotUserRequestException(String message) {
        super(message);
    }

    public BotUserRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public BotUserRequestException(Throwable cause) {
        super(cause);
    }
}
