package com.codepath.instagram.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.codepath.instagram.R;
import com.codepath.instagram.adapters.InstagramCommentsAdapter;
import com.codepath.instagram.core.MainApplication;
import com.codepath.instagram.helpers.Constants;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramComment;
import com.codepath.instagram.networking.InstagramClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    private List<InstagramComment> comments = new ArrayList<>();
    private InstagramCommentsAdapter adapter;
    private InstagramClient instagramClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        setTitle(getString(R.string.comments_title));

        instagramClient = MainApplication.getRestClient();
        final Intent intent = getIntent();
        String mediaId = intent.getStringExtra(Constants.INTENT_PAYLOAD_MEDIA_ID);

        adapter = new InstagramCommentsAdapter(comments);

        initRecyclerView();

        fetchComments(mediaId);
    }

    private void initRecyclerView() {
        RecyclerView rvComments = (RecyclerView) findViewById(R.id.rvComments);
        rvComments.setAdapter(adapter);
        rvComments.setLayoutManager(new LinearLayoutManager(this));
    }

    private void fetchComments(String mediaId) {
        instagramClient.getMediaComments(mediaId, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                List<InstagramComment> newComments = Utils.decodeCommentsFromJsonResponse(response);
                comments.clear();
                comments.addAll(newComments);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                String msg = "Failed to get comments for media: " + String.valueOf(statusCode);
                Toast.makeText(CommentsActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}
