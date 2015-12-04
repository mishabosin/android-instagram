package com.codepath.instagram.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramPost;
import com.codepath.instagram.models.InstagramPosts;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

public class InstagramNetworkService extends IntentService {

    public static final String SERVICE_NAME = "instagram-network-service";

    public static final String LOG_TAG = "InstagramNetworkService";
    public static final String INTENT_STATUS_CODE = "INTENT_STATUS_CODE";
    public static final String INTENT_DATA = "INTENT_DATA";

    public static final String ACTION_SUCCESS = "ACTION_SUCCESS";
    public static final String ACTION_FAILURE = "ACTION_FAILURE";

    private final AsyncHttpClient aClient = new SyncHttpClient();

    // Must create a default constructor
    public InstagramNetworkService() {
        // Used to name the worker thread, important only for debugging.
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }

        // TODO: get the self feed using the token
        // String url = "https://api.instagram.com/v1/users/self/feed";
        // InstagramClient instagramClient = MainApplication.getRestClient();
        // String token = client.getAccessToken().getToken
        // RequestParams params = new RequestParams("access_token", token);

        String url = "https://api.instagram.com/v1/media/popular";
        RequestParams params = new RequestParams("client_id", "e05c462ebd86446ea48a5af73769b602");

        aClient.get(this, url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                List<InstagramPost> newPosts = Utils.decodePostsFromJsonResponse(response);
                InstagramPosts data = new InstagramPosts(newPosts);

                Intent broadcast = new Intent(ACTION_SUCCESS);
                broadcast.putExtra(INTENT_STATUS_CODE, statusCode);
                broadcast.putExtra(INTENT_DATA, data);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcast);
                Log.d(LOG_TAG, "onSuccess");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Intent broadcast = new Intent(ACTION_FAILURE);
                broadcast.putExtra(INTENT_STATUS_CODE, statusCode);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcast);
                Log.d(LOG_TAG, "onFailure");
            }
        });
    }
}
