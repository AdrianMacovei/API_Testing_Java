package io.dummy_api.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)

@Configuration
@PropertySource("classpath:config.properties")
public class Properties {

    @Value("${project.id}")
    private String URI;

    @Value("${project.uri}")
    private String APP_ID;

    public String getApiUri()
    {
        return getURI();
    }

    public String getAppId()
    {
        return getAPP_ID();
    }
}

