package com.example.xss.config;

import com.example.xss.escapes.HtmlCharacterEscapes;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig {

  private final ObjectMapper objectMapper;

  @Bean
  public MappingJackson2HttpMessageConverter jsonEscapeConverter(){
    ObjectMapper mapper = objectMapper.copy();
    mapper.getFactory().setCharacterEscapes(new HtmlCharacterEscapes());

    return new MappingJackson2HttpMessageConverter(mapper);
  }

}
