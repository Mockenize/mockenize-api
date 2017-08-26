package io.github.mockenize.core.service;

import io.github.mockenize.core.domain.MockEntity;
import io.github.mockenize.core.exception.MockNotFoundException;
import io.github.mockenize.core.repository.MockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Service
public class MockService {

    private MockRepository mockRepository;

    @Autowired
    public MockService(MockRepository mockRepository) {
        this.mockRepository = mockRepository;
    }

    public List<MockEntity> searchMocks() {
        Iterable<MockEntity> mocksIterable = mockRepository.findAll();
        return StreamSupport.stream(mocksIterable.spliterator(), true).collect(toList());
    }

    public MockEntity createMock(MockEntity mockEntity) {
        return mockRepository.save(mockEntity);
    }

    public MockEntity retrieveMock(UUID id) {
        MockEntity mockEntity = mockRepository.findOne(id);

        if (mockEntity == null) {
            throw new MockNotFoundException(id);
        }

        return mockEntity;
    }

    public MockEntity updateMock(MockEntity mockEntity) {
        return mockRepository.save(mockEntity);
    }

    public void deleteMock(UUID id) {
        mockRepository.delete(id);
    }

    public void deleteMocks() {
        mockRepository.deleteAll();
    }
}
