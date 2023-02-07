package io.dummy_api.enums;

public enum Gender {
    MALE("male"),
    FEMALE("female"),
    OTHER("other"),
    EMPTY("");

    Gender(String genderType) {
        this.genderType = genderType;
    }
    public String getGenderType() {return genderType;}

    private final String genderType;
}
