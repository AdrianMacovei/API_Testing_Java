package io.dummy_api.models;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
public class PostModel {

    private String id;

    private String image;


    private String likes;


    private ArrayList<String> tags;


    private String text;

    private String link;

//    private Date publishDate;
    private String publishDate;

//    private Date updatedDate;
    private String updatedDate;

    private UserModel owner;

    public PostModel()
    {

    }

    public PostModel(String text, String image, String likes, ArrayList<String> tags, String link, UserModel user)
    {
        this.image = image;
        this.likes = likes;
        this.tags = tags;
        this.text = text;
        this.owner = user;
        this.link = link;
    }
}
