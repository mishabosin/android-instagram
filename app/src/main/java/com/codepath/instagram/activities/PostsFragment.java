package com.codepath.instagram.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.codepath.instagram.R;
import com.codepath.instagram.adapters.InstagramPostsAdapter;
import com.codepath.instagram.core.MainApplication;
import com.codepath.instagram.helpers.Constants;
import com.codepath.instagram.helpers.SimpleVerticalSpacerItemDecoration;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.listeners.OnAllCommentsClickListener;
import com.codepath.instagram.listeners.OnDotsClickListener;
import com.codepath.instagram.models.InstagramPost;
import com.codepath.instagram.networking.InstagramClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {
    private List<InstagramPost> posts = new ArrayList<>();
    InstagramPostsAdapter postsAdapter;
    InstagramClient instagramClient;

    public static PostsFragment newInstance() {
        return new PostsFragment();
    }

    public PostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        instagramClient = MainApplication.getRestClient();
        Context context = view.getContext();

        initAdapter(context);
        initRecyclerView(view);

        fetchPosts(context);
    }

    private void initRecyclerView(View view) {
        RecyclerView rvPosts = (RecyclerView) view.findViewById(R.id.rvPosts);
        int spacing = getResources().getInteger(R.integer.post_spacing);
        SimpleVerticalSpacerItemDecoration spacingDecoration =
                new SimpleVerticalSpacerItemDecoration(spacing);
        rvPosts.addItemDecoration(spacingDecoration);
        rvPosts.setAdapter(postsAdapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    private void initAdapter(Context context) {
        postsAdapter = new InstagramPostsAdapter(posts);
        postsAdapter.setOnAllCommentsClickListener(new OnAllCommentsClickListener() {
            @Override
            public void onCommentsClick(int position, View itemView) {
                startAllCommentsActivity(position, itemView);
            }
        });
        postsAdapter.setOnShareClickListener(new OnDotsClickListener() {
            @Override
            public void onClick(View ivDots, View itemView) {
                showDotsPopup(ivDots, itemView);
            }
        });
    }

    private void fetchPosts(final Context context) {
        instagramClient.getMyFeed(
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
                        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        String msg = "Failed to get Instagram feed: " + String.valueOf(statusCode);
                        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void startAllCommentsActivity(int position, View itemView) {
        InstagramPost targetPost = posts.get(position);
        // super lame sanity check
        if (targetPost == null) {
            return;
        }

        Intent intent = new Intent(itemView.getContext(), CommentsActivity.class);
        intent.putExtra(Constants.INTENT_PAYLOAD_MEDIA_ID, targetPost.mediaId);
        startActivity(intent);
    }

    // Display anchored popup menu based on view selected
    private void showDotsPopup(View ivDots, final View itemView) {
        PopupMenu popup = new PopupMenu(itemView.getContext(), ivDots);
        // Inflate the menu from xml
        popup.getMenuInflater().inflate(R.menu.popup_post, popup.getMenu());
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mnuShare:
                        startSharePostActivity(itemView);
                        return true;
                    default:
                        return false;
                }
            }
        });
        // Handle dismissal with: popup.setOnDismissListener(...);
        // Show the menu
        popup.show();
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
            Toast.makeText(itemView.getContext(), "Failed to share image", Toast.LENGTH_SHORT).show();
        }
    }
}
