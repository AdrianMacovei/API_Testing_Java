package io.dummy_api.requests;

import io.dummy_api.core.RestRequest;
import io.dummy_api.core.RestWrapper;
import io.dummy_api.models.ErrorUserModel;
import io.dummy_api.models.TagsModel;
import org.springframework.http.HttpMethod;

public class TagsRequests extends ModelRequest<TagsRequests>{

    public TagsRequests(RestWrapper restWrapper)
    {
        super(restWrapper);
    }

    public TagsModel getTags()
    {
        RestRequest restRequest = RestRequest.simpleRequest(HttpMethod.GET, "tag");
        return restWrapper.executeRequestAndProcessModel(TagsModel.class, restRequest);
    }

    public ErrorUserModel getTagsError()
    {
        RestRequest restRequest = RestRequest.simpleRequest(HttpMethod.GET, "tag");
        return restWrapper.executeRequestAndProcessModel(ErrorUserModel.class, restRequest);
    }

}
