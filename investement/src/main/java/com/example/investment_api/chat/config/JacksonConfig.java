package com.example.investment_api.chat.config;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

    @Bean("chatObjectMapper")  // 이름을 chatObjectMapper로 설정하여 구분
    public ObjectMapper chatObjectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .modulesToInstall(new JavaTimeModule())  // JavaTimeModule을 추가하여 LocalDateTime 처리
                .build();
    }
}
