package io.dummy_api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.HashMap;

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

    @JsonProperty(required = true)
    private String link;

//    private Date publishDate;
    private String publishDate;

//    private Date updatedDate;
    private String updatedDate;


    @JsonProperty(required = true)
    private UserModel owner;

    public PostModel()
    {

    }

    public PostModel(String text, String image, int likes, ArrayList<String> tags, String link, UserModel user)
    {
        this.image = image;
        this.likes = likes;
        this.tags = tags;
        this.text = text;
        this.owner = user;
        this.link = link;
    }


    public static HashMap<String, Object> generateRandomPost(UserModel userModel)
    {
//        ArrayList<String> randomTags = new ArrayList<>();
//        randomTags.add("image"); randomTags.add("life");
//        return new PostModel("Some image in my post", "https://loremflickr.com/320/240",
//                0, randomTags, "some link", userModel);
        HashMap<String, Object> newPost = new HashMap<>();
        newPost.put("image", "https://loremflickr.com/320/240");
        newPost.put("like", 0);
        newPost.put("tags", RandomStringUtils.random(4, true, false));
        newPost.put("text", RandomStringUtils.random(10, true, false));
        newPost.put("owner", userModel.getId());
        newPost.put("link", RandomStringUtils.random(4, true, false));
        return newPost;
    }
}
