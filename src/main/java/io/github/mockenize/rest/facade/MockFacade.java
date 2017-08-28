package io.github.mockenize.rest.facade;

import io.github.mockenize.core.domain.MockEntity;
import io.github.mockenize.core.service.MockService;
import io.github.mockenize.rest.domain.MockDetails;
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
public class MockFacade {

    private static final Type MOCK_DTO_LIST_TYPE = new TypeToken<List<MockDetails>>() {}.getType();

    private MockService mockService;

    private ModelMapper modelMapper;

    @Autowired
    public MockFacade(MockService mockService, ModelMapper modelMapper) {
        this.mockService = mockService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<MockDetails>> listMocks(@RequestParam("serverId") UUID serverId) {
        List<MockEntity> mocks = mockService.searchMocks(serverId);
        List<MockDetails> dtos = modelMapper.map(mocks, MOCK_DTO_LIST_TYPE);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<MockDetails> createMock(@RequestBody @Valid MockDetails requestBody) {
        MockEntity mockEntity = modelMapper.map(requestBody, MockEntity.class);
        MockEntity createdMockEntity = mockService.createMock(mockEntity);
        MockDetails createdMockDetails = modelMapper.map(createdMockEntity, MockDetails.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMockDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MockDetails> updateMock(@PathVariable("id") UUID id, @RequestBody @Valid MockDetails requestBody) {
        MockEntity mockEntity = mockService.retrieveMock(id);
        modelMapper.map(requestBody, mockEntity);
        MockEntity updatedMockEntity = mockService.updateMock(mockEntity);
        MockDetails updatedMockDetails = modelMapper.map(updatedMockEntity, MockDetails.class);
        return ResponseEntity.ok(updatedMockDetails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MockDetails> retrieveMock(@PathVariable("id") UUID id) {
        MockEntity mockEntity = mockService.retrieveMock(id);
        MockDetails mockDetails = modelMapper.map(mockEntity, MockDetails.class);
        return ResponseEntity.ok(mockDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMock(UUID id) {
        mockService.deleteMock(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMocks(@RequestParam("serverId") UUID serverId) {
        mockService.deleteMocks(serverId);
        return ResponseEntity.ok().build();
    }
}
