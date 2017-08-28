package io.github.mockenize.rest.facade;

import io.github.mockenize.core.domain.ServerEntity;
import io.github.mockenize.core.domain.ServerStatus;
import io.github.mockenize.core.service.ServerService;
import io.github.mockenize.rest.domain.ServerDetails;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/servers")
public class ServerFacade {

    private ServerService serverService;

    private ModelMapper modelMapper;

    @Autowired
    public ServerFacade(ServerService serverService, ModelMapper modelMapper) {
        this.serverService = serverService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ServerEntity> listServers() {
        Iterable<ServerEntity> serverEntities = serverService.search();
        return modelMapper.map(serverEntities, new TypeToken<List<ServerDetails>>(){}.getType());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServerDetails retrieveServer(@PathVariable("id") UUID id) {
        ServerEntity serverEntity = serverService.retrieveServer(id);
        return modelMapper.map(serverEntity, ServerDetails.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServerDetails createServer(@RequestBody @Valid ServerDetails serverDetails) {
        ServerEntity serverEntity = modelMapper.map(serverDetails, ServerEntity.class);
        ServerEntity createdEntity = serverService.createServer(serverEntity);
        return modelMapper.map(createdEntity, ServerDetails.class);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServerDetails updateServer(@PathVariable("id") UUID id, @RequestBody @Valid ServerDetails serverDetails) {
        ServerEntity serverEntity = serverService.retrieveServer(id);
        modelMapper.map(serverDetails, serverEntity);
        ServerEntity updatedEntity = serverService.updateServer(serverEntity);
        return modelMapper.map(updatedEntity, ServerDetails.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteServer(@PathVariable("id") UUID id) {
        serverService.deleteServer(id);
    }

    @PostMapping("/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public void startServer(@PathVariable("id") UUID id, @RequestBody @NotNull ServerStatus status) {
        serverService.updateServerStatus(id, status);
    }
}
