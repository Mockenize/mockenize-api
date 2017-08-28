package io.github.mockenize.core.service;

import io.github.mockenize.core.domain.MockEntity;
import io.github.mockenize.core.exception.MockNotFoundException;
import io.github.mockenize.core.repository.MockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MockService {

    private MockRepository mockRepository;

    private ServerService serverService;

    @Autowired
    public MockService(MockRepository mockRepository, ServerService serverService) {
        this.mockRepository = mockRepository;
        this.serverService = serverService;
    }

    public List<MockEntity> searchMocks(UUID serverId) {
        return mockRepository.findByServerId(serverId);
    }

    public MockEntity createMock(MockEntity mockEntity) {
        mockEntity.setId(UUID.randomUUID());
        MockEntity createdMock = mockRepository.save(mockEntity);
        serverService.reloadServer(mockEntity.getServerId());
        return createdMock;
    }

    public MockEntity retrieveMock(UUID id) {
        MockEntity mockEntity = mockRepository.findOne(id);

        if (mockEntity == null) {
            throw new MockNotFoundException(id);
        }

        return mockEntity;
    }

    public MockEntity updateMock(MockEntity mockEntity) {
        MockEntity updatedMock = mockRepository.save(mockEntity);
        serverService.reloadServer(mockEntity.getServerId());
        return updatedMock;
    }

    public void deleteMock(UUID id) {
        MockEntity mockEntity = mockRepository.findOne(id);

        if (mockEntity == null) {
            return;
        }

        mockRepository.delete(mockEntity);
        serverService.reloadServer(mockEntity.getServerId());
    }

    public void deleteMocks(UUID serverId) {
        mockRepository.deleteAllByServerId(serverId);
        serverService.reloadServer(serverId);
    }
}
