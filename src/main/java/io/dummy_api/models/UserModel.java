package io.dummy_api.models;

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

    private String firstName;

    private String lastName;

    private String email;

    private String gender;

    private String phone;

    private URL picture;

    private Date dateOfBirth;

    private Date registerDate;

    private Date updateDate;

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

//    public static UserModel generateRandomUser()
//    {
//        String randomData = RandomStringUtils.randomAlphabetic(7);
//    }

}
