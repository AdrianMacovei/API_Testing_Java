package io.dummy_api.exception;

public class JsonToModelConversionException extends RuntimeException
{
    public <T> JsonToModelConversionException(Class<T> classz, Exception e)
    {
        super(String.format("Could not parse JSON Response to model [%s] error %s", classz.getName(), e.getMessage()));
    }
}
