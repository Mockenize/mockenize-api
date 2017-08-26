package io.github.mockenize.rest.facade;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import io.github.mockenize.core.domain.MockEntity;
import io.github.mockenize.core.repository.MockRepository;
import io.github.mockenize.rest.domain.MockDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class MockFixtureHelper {

    private static final String RESOURCES_FIXTURES_PATH = "fixtures/%s";

    @Autowired
    private MockRepository mockRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    public Iterable<MockEntity> loadMocks(String fileName) throws IOException {
        String path = String.format(RESOURCES_FIXTURES_PATH, fileName);
        List<MockDto> mockDtos = objectMapper.readValue(Resources.getResource(path), new TypeReference<List<MockDto>>(){});
        List<MockEntity> mockEntities = modelMapper.map(mockDtos, new TypeToken<List<MockEntity>>(){}.getType());
        return mockRepository.save(mockEntities);
    }

    public MockEntity loadMock(String fileName) throws IOException {
        String path = String.format(RESOURCES_FIXTURES_PATH, fileName);
        MockDto mockDto = objectMapper.readValue(Resources.getResource(path), MockDto.class);
        MockEntity mockEntity = modelMapper.map(mockDto, MockEntity.class);
        return mockRepository.save(mockEntity);
    }

    public void clear() {
        mockRepository.deleteAll();
    }
}
