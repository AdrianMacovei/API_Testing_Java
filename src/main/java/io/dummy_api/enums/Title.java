package io.dummy_api.enums;

public enum Title
{
    MR("mr"),
    MS("ms"),
    MRS("mrs"),
    MISS("miss"),
    DR("dr"),
    EMPTY("");

    Title(String titleType) {
        this.titleType = titleType;
    }
    public String getTitleType() {return titleType;}

    private final String titleType;
}
