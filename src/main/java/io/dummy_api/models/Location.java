package io.dummy_api.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {

    private String street;
    private String city;
    private String state;
    private String country;
    private String timezone;

    public Location(){
    }

    public Location(String street, String city, String state, String country, String timezone) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.timezone = timezone;
    }
}
