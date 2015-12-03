package com.codepath.instagram.activities;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.instagram.R;
import com.codepath.instagram.adapters.SearchUserResultsAdapter;
import com.codepath.instagram.core.MainApplication;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramUser;
import com.codepath.instagram.networking.InstagramClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchUsersResultFragment extends SearchResultsFragment {
    InstagramClient instagramClient;
    List<InstagramUser> users;
    SearchUserResultsAdapter adapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        instagramClient = MainApplication.getRestClient();
        users = new ArrayList<>();

        initAdapter();
        initRecyclerView(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_users_result, container, false);
    }

    public static SearchUsersResultFragment newInstance() {
        return new SearchUsersResultFragment();
    }

    private void initAdapter() {
        adapter = new SearchUserResultsAdapter(users);
    }

    private void initRecyclerView(View view) {
        RecyclerView rvPosts = (RecyclerView) view.findViewById(R.id.rvUsers);
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    @Override
    public  void search(String query) {
        instagramClient.searchUsers(query, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                List<InstagramUser> newUsers = Utils.decodeUsersFromJsonResponse(response);
                users.clear();
                users.addAll(newUsers);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                String msg = "Failed to search for Users: " + String.valueOf(statusCode);
                 Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                String msg = "Failed to search for Users: " + String.valueOf(statusCode);
                 Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}
