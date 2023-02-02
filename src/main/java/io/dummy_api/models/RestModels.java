package io.dummy_api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RestModels<Model>
{
    @JsonProperty(value = "data")
    private List<Model> data;

    private int total;
    private int page;
    private int limit;
}
