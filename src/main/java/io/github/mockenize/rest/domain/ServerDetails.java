package io.github.mockenize.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.mockenize.core.domain.ServerStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@JsonIgnoreProperties(value = {"id", "status"}, allowGetters = true)
public class ServerDetails {

    @Id
    private UUID id;

    @NotEmpty
    private String name;

    @NotNull
    @Min(0)
    @Max(65535)
    private Integer port;

    private ServerStatus status;
}
