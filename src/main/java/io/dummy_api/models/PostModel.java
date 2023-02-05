package io.dummy_api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
public class PostModel {

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
    private UserModel owner;

    public PostModel()
    {

    }

    public PostModel(String text, String image, int likes, ArrayList<String> tags, UserModel owner)
    {
        this.image = image;
        this.likes = likes;
        this.tags = tags;
        this.text = text;
        this.owner = owner;
    }

    public static PostModel generateRandomPost(UserModel userModel)
    {
        ArrayList<String> randomTags = new ArrayList<>();
        randomTags.add("image"); randomTags.add("life");
        return new PostModel("Some image in my post", "https://loremflickr.com/320/240",
                0, randomTags, userModel);
    }
}
