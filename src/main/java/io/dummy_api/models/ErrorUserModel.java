package io.dummy_api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorUserModel {

    @JsonProperty(required = true)
    private String error;

//    private Object data;
    private UserModel data;
}
