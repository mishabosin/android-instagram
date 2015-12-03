package com.codepath.instagram.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initPager(view);
        return view;
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
