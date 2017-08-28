package io.github.mockenize.core.server.handler.predicate;

import io.github.mockenize.core.domain.MockResponse;
import io.undertow.predicate.Predicate;
import org.springframework.stereotype.Component;

import static io.undertow.attribute.ExchangeAttributes.requestMethod;
import static io.undertow.predicate.Predicates.*;

@Component
public class PredicateFactory {

    public Predicate create(String path, MockResponse mockResponse) {
        String method = mockResponse.getMethod().name();
        return and(
                path(path),
                contains(requestMethod(), method)
        );
    }
}
