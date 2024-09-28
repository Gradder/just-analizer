package com.gradder.justanalizer.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gradder.justanalizer.model.LegionBookParserRules;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ParserConfig {

    private final ObjectMapper om;

    @Bean
    @SneakyThrows
    public LegionBookParserRules legionBookParserRules() {
        return om.readValue(new File("src/main/resources/parser/rules/legions.json"), LegionBookParserRules.class);
    }

    @Bean
    @SneakyThrows
    public Map<String, String> legionsNumberMap() {
        TypeReference<Map<String, String>> refMap = new TypeReference<>() {};
        return om.readValue(new File("src/main/resources/parser/mappings/number-to-name.json"), refMap);
    }


}
