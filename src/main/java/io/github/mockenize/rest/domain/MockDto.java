package io.github.mockenize.rest.domain;

import io.github.mockenize.core.domain.MockMethod;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class MockDto {

    @Id
    private UUID id;

    @NotEmpty
    private String path;

    @NotNull
    private MockMethod method;

    @NotNull
    private List<MockResponseDto> responses;
}
