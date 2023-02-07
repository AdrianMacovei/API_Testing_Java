package io.dummy_api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import java.net.URL;
import java.util.Date;

@Getter
@Setter
public class UserModel {

    private String id;

    private String title;
    @JsonProperty(required = true)
    private String firstName;
    @JsonProperty(required = true)
    private String lastName;
    @JsonProperty(required = true)
    private String email;

    private String gender;

    private String phone;

    private URL picture;

    private String dateOfBirth;

    private Date registerDate;

    private Date updatedDate;

    private Location location;

    public UserModel()
    {

    }

    public UserModel(String firstName, String lastName, String email)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public static UserModel generateRandomUser()
    {
        String randomData = RandomStringUtils.randomAlphabetic(7);
        UserModel newUser = new UserModel("fn" + randomData, "ln" + randomData,
                randomData + "@gmail.com");
        newUser.setGender("male");
        newUser.setTitle("mr");

        return newUser;
    }

}
