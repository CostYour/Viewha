package ru.nuyaxov.pzviewha.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;

import java.io.IOException;

@Configuration
public class ExternalConfigConfiguration {

    @Bean
    public ExternalConfigPropertySource externalConfigPropertySource(ConfigurableEnvironment env) throws IOException {
        ExternalConfigPropertySource source = new ExternalConfigPropertySource();
        env.getPropertySources().addFirst(source);
        return source;
    }
}
