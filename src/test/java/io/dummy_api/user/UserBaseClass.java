package io.dummy_api.user;

import io.dummy_api.ApiBaseClass;
import io.dummy_api.enums.Gender;
import io.dummy_api.enums.Title;
import io.dummy_api.models.ErrorModel;
import io.dummy_api.models.UserModel;
import io.dummy_api.models.UsersCollection;
import io.restassured.response.Response;
import org.springframework.http.HttpMethod;
import org.testng.annotations.AfterMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;

public class UserBaseClass extends ApiBaseClass {

    @AfterMethod(alwaysRun = true, onlyForGroups = {"user_test"})
    protected void tearDown() {
        UsersCollection usersCollectionRsp = getCreatedUsers();
        for (int i = 0; i < usersCollectionRsp.getData().size(); i++) {
            deleteUser(usersCollectionRsp.getData().get(i).getId());
        }
    }

    public String createUser(UserModel userData) {
//        Response response = restWrapper.sendRequest(HttpMethod.POST,
//                "user/create", user_data, "");
        UserModel user = restWrapper.usingUsers().createUser(userData);
        return user.getId();
    }

    public UserModel getUser(String userId) {
        return restWrapper.usingUsers().getUser(userId);
    }

    public void deleteUser(String userId) {
        restWrapper.usingUsers().deleteUser(userId);
    }

    public UsersCollection getCreatedUsers() {
//        return restWrapper.sendRequest(HttpMethod.GET, "user?{params}", "", "created=1");
        return restWrapper.usingUsers().getCreatedUsers();
    }

    protected void verifyErrorDataMessageForRequiredFields(HashMap<String, String> user, ErrorModel errorRsp) {
        if (user.containsKey("lastName") && user.containsKey("email") && user.containsKey("firstName")) {
            if (user.get("firstName").length() == 1) {
                softAssert.assertEquals(errorRsp.getData().getFirstName(),
                        String.format("Path `firstName` (`%s`) is shorter than the minimum allowed length (2).",
                                user.get("firstName")));
            } else if (user.get("firstName").length() == 31) {
                softAssert.assertEquals(errorRsp.getData().getFirstName(),
                        String.format("Path `firstName` (`%s`) is longer than the maximum allowed length (30).",
                                user.get("firstName")));
            } else if (user.get("lastName").length() == 1) {
                softAssert.assertEquals(errorRsp.getData().getLastName(),
                        String.format("Path `lastName` (`%s`) is shorter than the minimum allowed length (2).",
                                user.get("lastName")));
            } else if (user.get("lastName").length() == 31) {
                softAssert.assertEquals(errorRsp.getData().getLastName(),
                        String.format("Path `lastName` (`%s`) is longer than the maximum allowed length (30).",
                                user.get("lastName")));
            } else if (!user.get("email").endsWith("@gmail.com")) {
                softAssert.assertEquals(errorRsp.getData().getEmail(),
                        String.format("Path `email` is invalid (%s).",
                                user.get("email")));
            }
        } else if (user.containsKey("lastName") && user.containsKey("email")) {
            softAssert.assertEquals(errorRsp.getData().getFirstName(), "Path `firstName` is required.");
        } else if (user.containsKey("lastName") && user.containsKey("firstName")) {
            softAssert.assertEquals(errorRsp.getData().getEmail(), "Path `email` is required.");
        } else if (user.containsKey("firstName") && user.containsKey("email")) {
            softAssert.assertEquals(errorRsp.getData().getLastName(), "Path `lastName` is required.");
        } else if (!user.containsKey("lastName") && !user.containsKey("firstName") && !user.containsKey("email")) {
            softAssert.assertEquals(errorRsp.getData().getFirstName(), "Path `firstName` is required.");
            softAssert.assertEquals(errorRsp.getData().getEmail(), "Path `email` is required.");
            softAssert.assertEquals(errorRsp.getData().getLastName(), "Path `lastName` is required.");
        } else if (user.containsKey("lastName")) {
            softAssert.assertEquals(errorRsp.getData().getFirstName(), "Path `firstName` is required.");
            softAssert.assertEquals(errorRsp.getData().getEmail(), "Path `email` is required.");
        } else if (user.containsKey("firstName")) {
            softAssert.assertEquals(errorRsp.getData().getLastName(), "Path `lastName` is required.");
            softAssert.assertEquals(errorRsp.getData().getEmail(), "Path `email` is required.");
        } else if (user.containsKey("email")) {
            softAssert.assertEquals(errorRsp.getData().getLastName(), "Path `lastName` is required.");
            softAssert.assertEquals(errorRsp.getData().getFirstName(), "Path `firstName` is required.");
        }

        softAssert.assertAll();
    }

    protected void verifyErrorDataNonRequiredFieldsUserCreate(HashMap<String, Object> user, ErrorModel errorRsp) {
        if (user.containsKey("title")) {
            String titleValue = user.get("title").toString();
            if (!titleValue.equals(Title.DR.getTitleType()) && !titleValue.equals(Title.MRS.getTitleType()) &&
                    !titleValue.equals(Title.MR.getTitleType()) && !titleValue.equals(Title.MS.getTitleType()) &&
                    !titleValue.equals(Title.MISS.getTitleType()) && !titleValue.equals(Title.EMPTY.getTitleType())) {
                softAssert.assertEquals(errorRsp.getData().getTitle(),
                        String.format("`%s` is not a valid enum value for path `title`.", user.get("title")));
            }
        } else if (user.containsKey("gender")) {
            String genderValue = user.get("gender").toString();
            if (!genderValue.equals(Gender.MALE.getGenderType()) && !genderValue.equals(Gender.FEMALE.getGenderType())
                    && !genderValue.equals(Gender.OTHER.getGenderType()) && !genderValue.equals(Gender.EMPTY.getGenderType())) {
                softAssert.assertEquals(errorRsp.getData().getGender(),
                        String.format("`%s` is not a valid enum value for path `gender`.", user.get("gender")));
            }

        } else if (user.containsKey("dateOfBirth")) {
            String dateOfBirthValue = user.get("dateOfBirth").toString();
            String minDateAllowed = "Mon Jan 01 1900 00:00:00 GMT+0000 (Coordinated Universal Time)";
            String maxDateAllowed = LocalDateTime.now().minusHours(5).toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                Calendar cal3 = Calendar.getInstance();

                cal1.setTime(sdf.parse(dateOfBirthValue));
                cal2.setTime(sdf.parse(minDateAllowed));
                cal3.setTime(sdf.parse(maxDateAllowed));


                if (cal1.before(cal2)) {
                    softAssert.assertEquals((errorRsp.getData().getDateOfBirth()),
                            String.format("Path `dateOfBirth` (%s) is before minimum allowed value (%s).",
                                    dateOfBirthValue, minDateAllowed));
                } else if (cal1.after(cal3)) {
                    softAssert.assertEquals((errorRsp.getData().getDateOfBirth()),
                            String.format("Path `dateOfBirth` (%s) is after maximum allowed value (1675772882847).",
                                    dateOfBirthValue));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (user.containsKey("phone")) {
            String phoneValue = user.get("phone").toString();
            int maxAllowed = 20;
            int minAllowed = 5;
            if (phoneValue.length() > maxAllowed) {
                softAssert.assertEquals((errorRsp.getData().getPhone()),
                        String.format("Path `phone` (`%s`) is longer than the maximum allowed length (%s).",
                                phoneValue, maxAllowed));
            } else if (phoneValue.length() < minAllowed) {
                softAssert.assertEquals((errorRsp.getData().getPhone()),
                        String.format("Path `phone` (`%s`) is shorter than the minimum allowed length (%s).",
                                phoneValue, minAllowed));
            }
        }

        softAssert.assertAll();
    }

}
