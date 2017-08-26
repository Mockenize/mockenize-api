package io.github.mockenize.rest.facade.admin;

import io.github.mockenize.core.domain.MockEntity;
import io.github.mockenize.core.service.MockService;
import io.github.mockenize.rest.domain.MockDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/mocks",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE,
        headers = "X-MockenizeAdmin=true"
)
public class MockAdminFacade {

    private static final Type MOCK_DTO_LIST_TYPE = new TypeToken<List<MockDto>>() {}.getType();

    private MockService mockService;

    private ModelMapper modelMapper;

    @Autowired
    public MockAdminFacade(MockService mockService, ModelMapper modelMapper) {
        this.mockService = mockService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<MockDto>> listMocks() {
        List<MockEntity> mocks = mockService.searchMocks();
        List<MockDto> dtos = modelMapper.map(mocks, MOCK_DTO_LIST_TYPE);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<MockDto> createMock(@RequestBody @Valid MockDto requestBody) {
        MockEntity mockEntity = modelMapper.map(requestBody, MockEntity.class);
        MockEntity createdMockEntity = mockService.createMock(mockEntity);
        MockDto createdMockDto = modelMapper.map(createdMockEntity, MockDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMockDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MockDto> updateMock(@PathVariable("id") UUID id, @RequestBody @Valid MockDto requestBody) {
        MockEntity mockEntity = mockService.retrieveMock(id);
        modelMapper.map(requestBody, mockEntity);
        MockEntity updatedMockEntity = mockService.updateMock(mockEntity);
        MockDto updatedMockDto = modelMapper.map(updatedMockEntity, MockDto.class);
        return ResponseEntity.ok(updatedMockDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MockDto> retrieveMock(@PathVariable("id") UUID id) {
        MockEntity mockEntity = mockService.retrieveMock(id);
        MockDto mockDto = modelMapper.map(mockEntity, MockDto.class);
        return ResponseEntity.ok(mockDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMock(UUID id) {
        mockService.deleteMock(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMocks() {
        mockService.deleteMocks();
        return ResponseEntity.ok().build();
    }
}
