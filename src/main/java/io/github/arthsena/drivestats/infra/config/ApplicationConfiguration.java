package io.github.arthsena.drivestats.infra.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Dependent
public class ApplicationConfiguration {

    @Singleton
    @Produces
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}