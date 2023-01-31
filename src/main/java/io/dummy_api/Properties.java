package io.dummy_api;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Configuration
@PropertySource("src.main.resources:default.properties")
public class Properties {

    @Value("${project.id}")
    private String URI;

    @Value("${project.uri}")
    private String APP_ID;


}

