package io.dummy_api.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class RestModels<T>
{
    private List<T> data;
    private int total;
    private int page;
    private int limit;
    private String error;
}
