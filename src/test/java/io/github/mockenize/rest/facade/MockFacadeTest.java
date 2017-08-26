package io.github.mockenize.rest.facade;

import io.github.mockenize.core.domain.MockEntity;
import io.github.mockenize.core.domain.MockMethod;
import io.github.mockenize.rest.domain.MockDto;
import io.github.mockenize.rest.domain.MockResponseDto;
import io.github.mockenize.rest.facade.admin.MockAdminFacade;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MockFacadeTest {

    @Autowired
    private MockAdminFacade mockFacade;

    @Autowired
    private MockFixtureHelper fixtureHelper;

    @After
    public void tearDown() throws Exception {
        fixtureHelper.clear();
    }

    @Test
    public void testListMocks() throws IOException {
        fixtureHelper.loadMocks("listMocks.json");

        ResponseEntity<List<MockDto>> response = mockFacade.listMocks();
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<MockDto> responseBody = response.getBody();
        assertEquals(4, responseBody.size());
    }

    @Test
    public void testRetrieveMock() throws Exception {
        Iterable<MockEntity> mockEntities = fixtureHelper.loadMocks("listMocks.json");
        MockEntity mockEntity = mockEntities.iterator().next();

        ResponseEntity<MockDto> response = mockFacade.retrieveMock(mockEntity.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());

        MockDto responseBody = response.getBody();
        assertNotNull(responseBody.getId());
        assertEquals(mockEntity.getMethod(), responseBody.getMethod());
        assertEquals(mockEntity.getPath(), responseBody.getPath());

        List<MockResponseDto> responses = responseBody.getResponses();
        assertEquals(1, responses.size());
        assertEquals(200, responses.get(0).getStatus().intValue());
        assertEquals(0, responses.get(0).getDelay().intValue());
        assertEquals(mockEntity.getResponses().get(0).getBody(), responses.get(0).getBody());
    }

    @Test
    public void testCreateMock() {
        MockResponseDto mockResponseDto = new MockResponseDto();
        mockResponseDto.setStatus(200);
        mockResponseDto.setDelay(0);
        mockResponseDto.setBody("{ \"key\": \"value\" }");

        MockDto requestBody = new MockDto();
        requestBody.setId(null);
        requestBody.setMethod(MockMethod.GET);
        requestBody.setPath("/test");
        requestBody.setResponses(Lists.newArrayList(mockResponseDto));

        ResponseEntity<MockDto> response = mockFacade.createMock(requestBody);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        MockDto responseBody = response.getBody();
        assertNotNull(responseBody.getId());
        assertEquals(requestBody.getMethod(), responseBody.getMethod());
        assertEquals(requestBody.getPath(), responseBody.getPath());

        List<MockResponseDto> responses = requestBody.getResponses();
        assertEquals(1, responses.size());
        assertEquals(200, responses.get(0).getStatus().intValue());
        assertEquals(0, responses.get(0).getDelay().intValue());
        assertEquals(mockResponseDto.getBody(), responses.get(0).getBody());
    }

    @Test
    public void testUpdateMock() throws Exception {
        MockEntity mockEntity = fixtureHelper.loadMock("updateMock.json");

        MockResponseDto mockResponseDto = new MockResponseDto();
        mockResponseDto.setStatus(400);
        mockResponseDto.setDelay(10);
        mockResponseDto.setBody("{ \"key\": \"value\" }");

        MockDto requestBody = new MockDto();
        requestBody.setMethod(MockMethod.GET);
        requestBody.setPath("/test");
        requestBody.setResponses(Lists.newArrayList(mockResponseDto));

        ResponseEntity<MockDto> response = mockFacade.updateMock(mockEntity.getId(), requestBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        MockDto responseBody = response.getBody();
        assertNotNull(responseBody.getId());
        assertEquals(requestBody.getMethod(), responseBody.getMethod());
        assertEquals(requestBody.getPath(), responseBody.getPath());

        List<MockResponseDto> responses = requestBody.getResponses();
        assertEquals(1, responses.size());
        assertEquals(400, responses.get(0).getStatus().intValue());
        assertEquals(10, responses.get(0).getDelay().intValue());
        assertEquals(mockResponseDto.getBody(), responses.get(0).getBody());
    }

    @Test
    public void testDeleteMock() throws Exception {
        Iterable<MockEntity> mockEntities = fixtureHelper.loadMocks("listMocks.json");
        MockEntity mockEntity = mockEntities.iterator().next();

        ResponseEntity<Void> response = mockFacade.deleteMock(mockEntity.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());

        assertEquals(3, mockFacade.listMocks().getBody().size());
    }

    @Test
    public void testDeleteMocks() throws Exception {
        fixtureHelper.loadMocks("listMocks.json");

        ResponseEntity<Void> response = mockFacade.deleteMocks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());

        assertEquals(0, mockFacade.listMocks().getBody().size());
    }
}