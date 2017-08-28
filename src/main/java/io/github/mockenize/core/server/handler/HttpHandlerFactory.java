package io.github.mockenize.core.server.handler;

import io.github.mockenize.core.domain.MockEntity;
import io.github.mockenize.core.server.handler.predicate.PredicateFactory;
import io.github.mockenize.core.server.handler.respose.ResponseFactory;
import io.github.mockenize.core.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Component
public class HttpHandlerFactory {

    private MockService mockService;

    private PredicateFactory predicateFactory;

    private ResponseFactory responseFactory;

    @Autowired
    public HttpHandlerFactory(MockService mockService,
                              PredicateFactory predicateFactory,
                              ResponseFactory responseFactory) {
        this.mockService = mockService;
        this.predicateFactory = predicateFactory;
        this.responseFactory = responseFactory;
    }

    public ServerHttpHandler create(UUID serverId) {
        List<MockEntity> mockEntities = mockService.searchMocks(serverId);
        List<MockHttpHandler> handlers = mockEntities.parallelStream()
                .flatMap(this::createHandlers)
                .collect(toList());
        return new ServerHttpHandler(handlers);
    }

    private Stream<MockHttpHandler> createHandlers(MockEntity mockEntity) {
        String path = mockEntity.getPath();
        return mockEntity.getResponses()
                .parallelStream()
                .map(r -> new MockHttpHandler(predicateFactory.create(path, r), responseFactory.create(r)));
    }
}
