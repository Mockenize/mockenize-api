package io.github.mockenize.core.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Document
public class ServerEntity {

    @Id
    @NotNull
    private UUID id;

    @NotEmpty
    private String name;

    @NotNull
    @Min(0)
    @Max(65535)
    private Integer port;

    @Min(0)
    @Max(65535)
    private Integer securePort;

    @NotEmpty
    private ServerStatus status = ServerStatus.STOPED;
}
