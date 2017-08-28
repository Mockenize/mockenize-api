package io.github.mockenize.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ServerNotFoundException extends RuntimeException {
    public ServerNotFoundException() {
        super("Server could not be found");
    }
}

