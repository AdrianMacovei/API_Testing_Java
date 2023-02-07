package io.dummy_api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ErrorModel {

    @JsonProperty(required = true)
    private String error;

//    private Object data;
    private UserModel data;
}
