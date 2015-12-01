package com.codepath.instagram.networking;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class InstagramClient {
    private static String CLIENT_ID = "e05c462ebd86446ea48a5af73769b602";
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static String POPULAR_FEED_URL = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;

    public static void getPopularFeed(JsonHttpResponseHandler responseHandler) {
        client.get(POPULAR_FEED_URL, null, responseHandler);
    }

}
