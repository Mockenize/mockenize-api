package io.github.mockenize.rest.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class JsonStringDeserializer extends StdDeserializer<String> {

    protected JsonStringDeserializer() {
        super(String.class);
    }

    @Override
    public String deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return parser.readValueAsTree().toString();
    }
}
