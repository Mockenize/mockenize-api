package io.github.mockenize.rest.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.mockenize.core.domain.MockMethod;
import io.github.mockenize.rest.json.JsonStringDeserializer;
import io.github.mockenize.rest.json.JsonStringSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Setter
public class MockResponseDetails {

    private Integer delay;

    private Integer status;

    @NotNull
    private MockMethod method;

    private Map<String, String> headers;

    @JsonSerialize(using = JsonStringSerializer.class)
    @JsonDeserialize(using = JsonStringDeserializer.class)
    private String body;
}
