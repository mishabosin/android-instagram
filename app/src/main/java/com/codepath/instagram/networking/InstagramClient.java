package com.codepath.instagram.networking;

import android.content.Context;

import com.codepath.instagram.helpers.Constants;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;

public class InstagramClient extends OAuthBaseClient {
    private static String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";

    public static final Class<? extends Api> REST_API_CLASS = InstagramApi.class;
    public static final String REST_URL = "https://api.instagram.com/v1";
    public static final String REST_CONSUMER_KEY = "e05c462ebd86446ea48a5af73769b602";
    public static final String REST_CONSUMER_SECRET = "7f18a14de6c241c2a9ccc9f4a3df4b35";
    public static final String REDIRECT_URI = Constants.REDIRECT_URI;
    public static final String SCOPE = Constants.SCOPE;

    public InstagramClient(Context context) {
        super(context, REST_API_CLASS, REST_URL,
                REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REDIRECT_URI, SCOPE);
    }

    public void getPopularFeed(JsonHttpResponseHandler responseHandler) {
        String url = getApiUrl("media/popular");
        client.get(url, responseHandler);
    }

    public void getMyFeed(JsonHttpResponseHandler responseHandler) {
        String url = getApiUrl("users/self/feed");
        client.get(url, responseHandler);
    }

    public void getMediaComments(String mediaId, JsonHttpResponseHandler responseHandler) {
        String url = getApiUrl("media/" + mediaId + "/comments?client_id=" + CLIENT_ID);
        client.get(url, null, responseHandler);
    }

    // https://api.instagram.com/v1/users/search?q={searchTerm}
    public void searchUsers(String query, JsonHttpResponseHandler responseHandler) {
        searchThing("users", query, responseHandler);
    }

    // https://api.instagram.com/v1/tags/search?q={searchTerm}
    public void searchTags(String query, JsonHttpResponseHandler responseHandler) {
        searchThing("tags", query, responseHandler);
    }

    private void searchThing(String thing, String query, JsonHttpResponseHandler responseHandler) {
        String url = getApiUrl(thing + "/search");

        RequestParams requestParams = new RequestParams();
        requestParams.put("q", query);

        client.get(url, requestParams, responseHandler);
    }
}
