package com.codepath.instagram.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.instagram.R;
import com.codepath.instagram.adapters.SearchTagResultsAdapter;
import com.codepath.instagram.core.MainApplication;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramSearchTag;
import com.codepath.instagram.networking.InstagramClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchTagResultsFragment extends SearchResultsFragment {
    List<InstagramSearchTag> tags;
    SearchTagResultsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_tag_results, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        tags = new ArrayList<>();

        initAdapter();
        initRecyclerView(view);
    }

    public static SearchTagResultsFragment newInstance() {
        return new SearchTagResultsFragment();
    }


    private void initAdapter() {
        adapter = new SearchTagResultsAdapter(tags);
    }

    private void initRecyclerView(View view) {
        RecyclerView rvTags = (RecyclerView) view.findViewById(R.id.rvTags);
        rvTags.setAdapter(adapter);
        rvTags.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    @Override
    public void search(String query) {
        InstagramClient instagramClient = MainApplication.getRestClient();
        instagramClient.searchTags(query, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                List<InstagramSearchTag> newTags = Utils.decodeSearchTagsFromJsonResponse(response);
                tags.clear();
                tags.addAll(newTags);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                String msg = "Failed to search for tags: " + String.valueOf(statusCode);
                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                String msg = "Failed to search for tags: " + String.valueOf(statusCode);
                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}
