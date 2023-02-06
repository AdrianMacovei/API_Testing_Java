package io.dummy_api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Getter
@Setter
public class CreateBodyPostModel {

    private String id;

    @JsonProperty(required = true)
    private String image;

    @JsonProperty(required = true)
    private int likes;

    @JsonProperty(required = true)
    private ArrayList<String> tags;

    @JsonProperty(required = true)
    private String text;

    private String link;

    private Date publishDate;

    @JsonProperty(required = true)
    private String owner;

    public CreateBodyPostModel() {

    }

    public CreateBodyPostModel(String text, String image, int likes, ArrayList<String> tags, String link, String owner) {
        this.image = image;
        this.likes = likes;
        this.tags = tags;
        this.text = text;
        this.owner = owner;
        this.link = link;
    }


    public static CreateBodyPostModel generateRandomPostBody(UserModel userModel) {
        ArrayList<String> randomTags = new ArrayList<>();
        randomTags.add(" " + RandomStringUtils.random(4, true, false));
        randomTags.add(RandomStringUtils.random(4, true, false));
        return new CreateBodyPostModel(
                RandomStringUtils.random(10, true, false),
                "https://loremflickr.com/320/240",
                0, randomTags,
                RandomStringUtils.random(4, true, false),
                userModel.getId());
    }

}
