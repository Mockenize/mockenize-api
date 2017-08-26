package io.github.mockenize.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class MockResponse {

    private Integer delay = 0;

    private Integer status;

    private Map<String, String> headers;

    private String body;
}
