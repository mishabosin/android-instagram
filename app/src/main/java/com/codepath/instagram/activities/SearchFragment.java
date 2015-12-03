package com.codepath.instagram.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.instagram.R;
import com.codepath.instagram.adapters.SearchFragmentStatePagerAdapter;

public class SearchFragment extends Fragment {
    private SearchFragmentStatePagerAdapter pagerAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initPager(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, final MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar.
        inflater.inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Fetch the data remotely
                triggerSearch(query);

                // Reset SearchView
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void triggerSearch(String query) {
        for (int i = 0; i < SearchFragmentStatePagerAdapter.NUM_OF_PAGES; i++) {
            SearchResultsFragment searchResultsFragment = (SearchResultsFragment) pagerAdapter.getRegisteredFragment(i);
            searchResultsFragment.search(query);
        }
    }

    private void initPager(View view) {
        Context context = getContext();
        ViewPager vpPager = (ViewPager) view.findViewById(R.id.vpSearchPager);
        pagerAdapter = new SearchFragmentStatePagerAdapter(getChildFragmentManager(), context);
        vpPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.searchTabs);
        tabLayout.setupWithViewPager(vpPager);
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }
}
