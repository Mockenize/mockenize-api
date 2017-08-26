package io.github.mockenize.core.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Document
public class MockEntity {

    @Id
    @NotNull
    private UUID id;

    @NotEmpty
    private String path;

    @NotNull
    private MockMethod method;

    @NotNull
    private List<MockResponse> responses;

    public MockEntity() {
        this.id = UUID.randomUUID();
    }
}
