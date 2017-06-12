package com.volia.eadmin.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public final class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JodaModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    private JsonUtil(){}

    public static <T> T toObject(String json, Class<T> target){
        try {
            return objectMapper.readValue(json, target);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String fromObject(Object target){
        try {
            return objectMapper.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }catch (Exception ex){
            log.info(ex.getMessage());
        }
        return "";
    }
}
