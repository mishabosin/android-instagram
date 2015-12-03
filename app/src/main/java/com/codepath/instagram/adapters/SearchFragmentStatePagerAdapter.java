package com.codepath.instagram.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.codepath.instagram.R;
import com.codepath.instagram.activities.SearchTagResultsFragment;
import com.codepath.instagram.activities.SearchUsersResultFragment;
import com.codepath.instagram.helpers.SmartFragmentStatePagerAdapter;

public class SearchFragmentStatePagerAdapter extends SmartFragmentStatePagerAdapter {
    private static int NUM_OF_PAGES = 2;
    private Context context;

    public SearchFragmentStatePagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SearchUsersResultFragment.newInstance();
            case 1:
                return SearchTagResultsFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.search_users_title);
            case 1:
                return context.getString(R.string.search_tags_title);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_OF_PAGES;
    }
}
