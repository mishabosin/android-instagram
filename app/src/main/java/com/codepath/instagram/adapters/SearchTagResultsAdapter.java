package com.codepath.instagram.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramSearchTag;

import java.util.List;

public class SearchTagResultsAdapter extends
        RecyclerView.Adapter<SearchTagResultsAdapter.SearchTagViewHolder> {

    List<InstagramSearchTag> tags;

    public SearchTagResultsAdapter(List<InstagramSearchTag> tags) {
        this.tags = tags;
    }

    public static class SearchTagViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTag;
        public TextView tvCount;

        public SearchTagViewHolder(View itemView) {
            super(itemView);
            tvTag = (TextView) itemView.findViewById(R.id.tvTag);
            tvCount = (TextView) itemView.findViewById(R.id.tvCount);
        }
    }

    @Override
    public SearchTagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_item_search_tags_result, parent, false);

        return new SearchTagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchTagViewHolder holder, int position) {
        InstagramSearchTag tag = tags.get(position);

        holder.tvTag.setText(tag.tag);
        holder.tvCount.setText(Utils.formatNumberForDisplay(tag.count));
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }
}
