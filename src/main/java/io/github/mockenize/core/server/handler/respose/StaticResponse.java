package io.github.mockenize.core.server.handler.respose;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class StaticResponse {

    private Map<String, String> headers;

    private Integer statusCode;

    private String body;
}
