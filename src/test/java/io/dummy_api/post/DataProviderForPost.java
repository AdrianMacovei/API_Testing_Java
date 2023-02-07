package io.dummy_api.post;

import io.dummy_api.models.PostModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;

public class DataProviderForPost {


    @DataProvider(name = "valid_update_data")
    public static Object[][] createValidDataPageParam() {

//        PostModel postModelValidMaxRangeForFieldImage = new PostModel();
//        postModelValidMaxRangeForFieldImage.setImage(RandomStringUtils.random(101, true, true));
//
//        PostModel postModelValidMinRangeForFieldImage = new PostModel();
//        postModelValidMinRangeForFieldImage.setImage(RandomStringUtils.random(1, true, true));
//
//        PostModel postModelValidMinRangeForFieldText = new PostModel();
//        postModelValidMinRangeForFieldText.setText(RandomStringUtils.random(6, true, true));
//
//        PostModel postModelValidMaxRangeForFieldText = new PostModel();
//        postModelValidMaxRangeForFieldText.setText(RandomStringUtils.random(50, true, true));

        PostModel postModelFullUpdateFields = new PostModel();
        postModelFullUpdateFields.setText(RandomStringUtils.random(50, true, true));
        postModelFullUpdateFields.setImage(RandomStringUtils.random(50, true, true));
        postModelFullUpdateFields.setLikes(101);
        ArrayList<String> tags = new ArrayList<>();
        tags.add(RandomStringUtils.random(50, true, true));
        tags.add(null);
        tags.add("");
        tags.add("   ");
        postModelFullUpdateFields.setTags(tags);




        return new PostModel[][]{
                {postModelFullUpdateFields}
//                {postModelValidMinRangeForFieldImage},
//                {postModelValidMinRangeForFieldImage},
//                {postModelValidMinRangeForFieldText},
//                {postModelValidMaxRangeForFieldText}

        };
    }


}
