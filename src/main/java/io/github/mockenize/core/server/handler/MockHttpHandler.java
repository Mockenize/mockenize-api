package io.github.mockenize.core.server.handler;

import io.github.mockenize.core.server.handler.respose.StaticResponse;
import io.undertow.predicate.Predicate;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HeaderMap;
import io.undertow.util.HttpString;

public class MockHttpHandler implements HttpHandler {

    private final Predicate predicate;

    private final StaticResponse response;

    public MockHttpHandler(Predicate predicate, StaticResponse response) {
        this.predicate = predicate;
        this.response = response;
    }

    public boolean match(HttpServerExchange exchange) {
        return predicate.resolve(exchange);
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        exchange.setStatusCode(response.getStatusCode());
        HeaderMap responseHeaders = exchange.getResponseHeaders();
        response.getHeaders().forEach((k, v) -> responseHeaders.put(new HttpString(k), v));
        exchange.getResponseSender().send(response.getBody());
    }
}
