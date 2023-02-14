package io.dummy_api.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

@Getter
@Setter
public class Location {

    private String street;
    private String city;
    private String state;
    private String country;
    private String timezone;

    public Location() {
    }

    public Location(String street, String city, String state, String country, String timezone) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.timezone = timezone;
    }

    public static Location generateRandomLocation()
    {
        Location randomLocation = new Location(
                RandomStringUtils.randomAlphabetic(5, 100),
                RandomStringUtils.randomAlphabetic(2, 30),
                RandomStringUtils.randomAlphabetic(2, 30),
                RandomStringUtils.randomAlphabetic(2, 30),
                String.format("+%s:00", RandomStringUtils.randomNumeric(12))
        );

        return randomLocation;
    }
}
