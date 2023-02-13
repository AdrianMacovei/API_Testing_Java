package io.dummy_api.user;

import io.dummy_api.ApiBaseClass;
import io.dummy_api.models.UserModel;
import io.dummy_api.models.UsersCollection;
import org.testng.annotations.AfterMethod;

public class UserBaseClass extends ApiBaseClass {

    protected static final String ERROR_DATA_MESSAGE_WRONG_TITLE = "`%s` is not a valid enum value for path `title`.";
    protected static final String ERROR_DATA_MESSAGE_WRONG_GENDER = "`%s` is not a valid enum value for path `gender`.";
    protected final String CREATED_USERS_PARAMS = "created=1";

    @AfterMethod(alwaysRun = true, onlyForGroups = {"user_test"})
    protected void tearDown() {
        System.out.println("*************************Tear Down Start************************");
        UsersCollection usersCollectionRsp = getCreatedUsers();
        if (usersCollectionRsp.getData().size() > 0)
        {
            for (int i = 0; i < usersCollectionRsp.getData().size(); i++) {
                deleteUser(usersCollectionRsp.getData().get(i).getId());
            }
        }
        System.out.println("*************************Tear Down Finish************************");
    }

    public String createUser(UserModel userData) {
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
        return restWrapper.usingUsers().usingParams(CREATED_USERS_PARAMS).getUsers();
    }
}
