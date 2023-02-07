package io.dummy_api.requests;

import io.dummy_api.core.RestWrapper;

public class ModelRequest <Request>
{
    protected RestWrapper restWrapper;

    public ModelRequest(RestWrapper restWrapper)
    {
        this.restWrapper = restWrapper;
    }

    public Request usingParams(String... parameters)
    {
        restWrapper.withParams(parameters);
        return (Request) this;
    }


}
