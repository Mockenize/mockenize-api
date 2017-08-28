package io.github.mockenize.core.server.handler.respose;

import io.github.mockenize.core.domain.MockResponse;
import org.springframework.stereotype.Component;

@Component
public class ResponseFactory {

    public StaticResponse create(MockResponse mockResponse) {
        StaticResponse response = new StaticResponse();
        response.setStatusCode(mockResponse.getStatus());
        response.setHeaders(mockResponse.getHeaders());
        response.setBody(mockResponse.getBody());
        return response;
    }
}
