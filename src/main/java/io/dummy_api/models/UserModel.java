package io.dummy_api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dummy_api.enums.Gender;
import io.dummy_api.enums.Title;
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
//    @JsonProperty(required = true)
    private String firstName;
//    @JsonProperty(required = true)
    private String lastName;
//    @JsonProperty(required = true)
    private String email;

    private String gender;

    private String phone;

    //changed URL to String format
    private String picture;

    private String dateOfBirth;

    private String registerDate;

    private String updatedDate;

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
    public UserModel(String firstName, String lastName, String email, String gender, String title)
    {
        this(firstName, lastName, email);
        this.gender = gender;
        this.title = title;
    }

    public UserModel(String firstName, String lastName, String email, String gender, String title,
                     String phone, String picture, String dateOfBirth, Location location)
    {
        this(firstName, lastName, email, gender, title);
        this.phone = phone;
        this.picture = picture;
        this.dateOfBirth = dateOfBirth;
        this.location = location;
    }

    public static UserModel generateRandomUser()
    {
        String randomData = RandomStringUtils.randomAlphabetic(10);
        UserModel newUser = new UserModel("fn" + randomData, "ln" + randomData,
                randomData.toLowerCase() + "@gmail.com");
        newUser.setGender("male");
        newUser.setTitle("mr");

        return newUser;
    }

    public static UserModel generateRandomAllFieldsUser()
    {
        String randomData = RandomStringUtils.randomAlphabetic(10);
        UserModel newUser = new UserModel("fn" + randomData, "ln" + randomData,
                randomData.toLowerCase() + "@gmail.com");
        newUser.setGender(Gender.MALE.getGenderType());
        newUser.setTitle(Title.MR.getTitleType());
        newUser.setPhone(RandomStringUtils.randomAlphabetic(5, 20));
//        newUser.setDateOfBirth(DataProviderClass);
        return newUser;
    }
}
