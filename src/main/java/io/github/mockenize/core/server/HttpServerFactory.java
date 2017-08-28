package io.github.mockenize.core.server;

import io.github.mockenize.core.domain.ServerEntity;
import io.github.mockenize.core.server.handler.HttpHandlerFactory;
import io.github.mockenize.core.server.handler.ServerHttpHandler;
import io.undertow.Undertow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
public class HttpServerFactory {

    private static final String HOSTNAME = "localhost";

    private HttpHandlerFactory httpHandlerFactory;

    @Autowired
    public HttpServerFactory(HttpHandlerFactory httpHandlerFactory) {
        this.httpHandlerFactory = httpHandlerFactory;
    }

    public Undertow createServer(ServerEntity serverEntity) {
        ServerHttpHandler handler = httpHandlerFactory.create(serverEntity.getId());

        return Undertow.builder()
                .addHttpListener(serverEntity.getPort(), HOSTNAME)
                .setHandler(handler)
                .addListener(new Undertow.ListenerBuilder())
                .build();
    }
}
