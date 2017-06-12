package com.volia.eadmin.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.volia.eadmin.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CustomDeserializer extends StdDeserializer<List<Message>> {
    @Autowired
    private MessageRepository messageRepository;

    public CustomDeserializer() {
        this(null);
    }

    public CustomDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public List<Message> deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        List ids = new ArrayList();
        JsonNode node = jp.getCodec().readTree(jp);
        for (int i = 0; i < node.size(); i++) {
            long entityId = (node.get(i)).longValue();
            ids.add(entityId);
        }
        List<Message> result = (List) StreamSupport.stream(messageRepository.findAll(ids).spliterator(), false).collect(Collectors.toList());
        return result;
    }
}
