package io.github.mockenize.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MockResponse {

    @NotNull
    @Min(0)
    private Integer delay = 0;

    @NotNull
    private Integer status;

    @NotNull
    private MockMethod method;

    @NotNull
    private Map<String, String> headers;

    private String body;
}
