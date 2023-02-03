package io.dummy_api.models;

import lombok.Getter;

@Getter
public class ErrorModel {
    private String error;
    private Object data;
}
