package io.github.mockenize.core.service;

import io.github.mockenize.core.domain.ServerEntity;
import io.github.mockenize.core.domain.ServerStatus;
import io.github.mockenize.core.exception.ServerNotFoundException;
import io.github.mockenize.core.repository.ServerRepository;
import io.github.mockenize.core.server.HttpServerFactory;
import io.undertow.Undertow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ServerService {

    private ServerRepository serverRepository;

    private HttpServerFactory httpServerFactory;

    private Map<UUID, Undertow> servers;

    @Autowired
    public ServerService(ServerRepository serverRepository, HttpServerFactory httpServerFactory) {
        this.serverRepository = serverRepository;
        this.httpServerFactory = httpServerFactory;
        this.servers = new ConcurrentHashMap<>();
    }

    public Iterable<ServerEntity> search() {
        return serverRepository.findAll();
    }

    public ServerEntity retrieveServer(UUID id) {
        ServerEntity serverEntity = serverRepository.findOne(id);

        if (serverEntity == null) {
            throw new ServerNotFoundException();
        }

        return serverEntity;
    }

    public ServerEntity createServer(ServerEntity serverEntity) {
        serverEntity.setId(UUID.randomUUID());
        return serverRepository.save(serverEntity);
    }

    public ServerEntity updateServer(ServerEntity serverEntity) {
        ServerEntity updatedServerEntity = serverRepository.save(serverEntity);
        reloadServer(serverEntity.getId());
        return updatedServerEntity;
    }

    public void reloadServer(UUID id) {
        stopServer(id);
        startServer(id);
    }

    public void deleteServer(UUID id) {
        stopServer(id);
        serverRepository.delete(id);
    }

    public void startServer(UUID id) {
        ServerEntity serverEntity = retrieveServer(id);

        if (servers.containsKey(serverEntity.getId()) && ServerStatus.RUNNING.equals(serverEntity.getStatus())) {
            return;
        }

        Undertow server = httpServerFactory.createServer(serverEntity);
        server.start();

        serverEntity.setStatus(ServerStatus.RUNNING);
        serverRepository.save(serverEntity);
        servers.put(serverEntity.getId(), server);
    }


    public void stopServer(UUID id) {
        ServerEntity serverEntity = retrieveServer(id);

        if (!servers.containsKey(serverEntity.getId())) {
            return;
        }

        Undertow server = servers.get(serverEntity.getId());
        server.stop();
        servers.remove(serverEntity.getId());

        serverEntity.setStatus(ServerStatus.STOPED);
        serverRepository.save(serverEntity);
    }

    public void updateServerStatus(UUID id, ServerStatus status) {
        switch (status) {
            case RUNNING:
                stopServer(id);
                break;

            case STOPED:
                startServer(id);
                break;
        }
    }
}
