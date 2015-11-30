package com.codepath.instagram.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.codepath.instagram.models.InstagramPost;

import java.util.List;

public class InstagramPostsAdapter extends
        RecyclerView.Adapter<InstagramPostsAdapter.PostViewHolder> {

    private List<InstagramPost> posts;

    public InstagramPostsAdapter(List<InstagramPost> posts) {
        this.posts = posts;
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserName;

        public PostViewHolder(View itemView) {
            super(itemView);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
        }
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View contactView = inflater.inflate(R.layout.layout_item_post, parent, false);

        return new PostViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        InstagramPost post = posts.get(position);
        holder.tvUserName.setText(post.user.userName);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
