package io.github.mockenize.rest.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.mockenize.rest.json.JsonStringDeserializer;
import io.github.mockenize.rest.json.JsonStringSerializer;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class MockResponseDto {

    private Integer delay = 0;

    private Integer status;

    private Map<String, String> headers;

    @JsonSerialize(using = JsonStringSerializer.class)
    @JsonDeserialize(using = JsonStringDeserializer.class)
    private String body;
}
