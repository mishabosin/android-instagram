package com.codepath.instagram.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.codepath.instagram.R;
import com.codepath.instagram.adapters.InstagramPostsAdapter;
import com.codepath.instagram.helpers.Constants;
import com.codepath.instagram.helpers.SimpleVerticalSpacerItemDecoration;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.listeners.OnAllCommentsClickListener;
import com.codepath.instagram.listeners.OnShareClickListener;
import com.codepath.instagram.models.InstagramPost;
import com.codepath.instagram.networking.InstagramClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private List<InstagramPost> posts = new ArrayList<>();
    InstagramPostsAdapter postsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        postsAdapter = new InstagramPostsAdapter(posts);
        postsAdapter.setOnAllCommentsClickListener(new OnAllCommentsClickListener() {
            @Override
            public void onCommentsClick(int position) {
                startAllCommentsActivity(position);
            }
        });
        postsAdapter.setOnShareClickListener(new OnShareClickListener() {
            @Override
            public void onShareClick(View itemView) {
                startSharePostActivity(itemView);
            }
        });

        RecyclerView rvPosts = (RecyclerView) findViewById(R.id.rvPosts);
        int spacing = getResources().getInteger(R.integer.post_spacing);
        SimpleVerticalSpacerItemDecoration spacingDecoration =
                new SimpleVerticalSpacerItemDecoration(spacing);
        rvPosts.addItemDecoration(spacingDecoration);
        rvPosts.setAdapter(postsAdapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(this));

        fetchPosts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchPosts() {
        InstagramClient.getPopularFeed(
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        List<InstagramPost> newPosts = Utils.decodePostsFromJsonResponse(response);
                        posts.clear();
                        posts.addAll(newPosts);
                        postsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        String msg = "Failed to get Instagram feed: " + String.valueOf(statusCode);
                        Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void startAllCommentsActivity(int position) {
        InstagramPost targetPost = posts.get(position);
        // super lame sanity check
        if (targetPost == null) {
            return;
        }

        Intent intent = new Intent(HomeActivity.this, CommentsActivity.class);
        intent.putExtra(Constants.INTENT_PAYLOAD_MEDIA_ID, targetPost.mediaId);
        startActivity(intent);
    }

    private void startSharePostActivity(View itemView) {
        ImageView ivGraphic = (ImageView) itemView.findViewById(R.id.ivGraphic);

        Uri bmpUri = Utils.getLocalBitmapUri(ivGraphic);
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");
            // Launch sharing dialog for image
            startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } else {
            // TODO: ...sharing failed, handle error
        }
    }
}
