package io.github.mockenize.core.server.handler;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

import java.util.List;
import java.util.Optional;

public class ServerHttpHandler implements HttpHandler {

    private final List<MockHttpHandler> handlers;

    public ServerHttpHandler(List<MockHttpHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        Optional<MockHttpHandler> match = handlers.stream()
                .filter(h -> h.match(exchange))
                .findFirst();

        if (!match.isPresent()) {
            exchange.setStatusCode(404).getResponseSender().close();
            return;
        }

        exchange.dispatch(match.get());
    }
}
