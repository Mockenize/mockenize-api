package io.github.mockenize.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(value = {"id"}, allowGetters = true)
public class MockDetails {

    @Id
    private UUID id;

    @NotNull
    private UUID serverId;

    @NotEmpty
    private String path;

    @NotNull
    private List<MockResponseDetails> responses;
}
