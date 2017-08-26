package io.github.mockenize.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class MockNotFoundException extends RuntimeException {
    public MockNotFoundException(UUID id) {
        super(String.format("Mock with id \"%s\" not found", id.toString()));
    }
}
