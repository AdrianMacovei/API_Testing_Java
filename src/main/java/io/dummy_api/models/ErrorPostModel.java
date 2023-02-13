package io.dummy_api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ErrorPostModel {

    @JsonProperty(required = true)
    private String error;

    private PostModel data;
}
