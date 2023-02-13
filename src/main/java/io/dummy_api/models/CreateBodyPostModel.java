package io.dummy_api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.function.Supplier;

@Getter
@Setter
public class CreateBodyPostModel {

//    private String id;
    @JsonProperty(required = true)
    private String image;

    @JsonProperty(required = true)
    private String likes;

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

    public CreateBodyPostModel(String text, String image, String likes, Supplier<ArrayList<String>> tagsSupplier, String link,
                               String owner) {
        this.image = image;
        this.likes = likes;
        this.tags = tagsSupplier.get();
        this.text = text;
        this.link = link;
        this.owner = owner;
    }

    public static CreateBodyPostModel generateRandomPostBody(UserModel userModel) {
        Random rand = new Random();
        return new CreateBodyPostModel(
                RandomStringUtils.random(rand.nextInt(900)+100, true, false),
                "https://loremflickr.com/320/240",
                "0",
                () -> {
                    ArrayList<String> randomTags = new ArrayList<>();
                    randomTags.add(" " + RandomStringUtils.random(4, true, false));
                    randomTags.add(RandomStringUtils.random(4, true, false));
                    return  randomTags;
                },
                RandomStringUtils.random(rand.nextInt(6)+100, true, false),
                userModel.getId());
    }

}
