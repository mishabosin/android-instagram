package com.codepath.instagram.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramPost;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InstagramPostsAdapter extends
        RecyclerView.Adapter<InstagramPostsAdapter.PostViewHolder> {

    private List<InstagramPost> posts;

    public InstagramPostsAdapter(List<InstagramPost> posts) {
        this.posts = posts;
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserName;
        public TextView tvTimeStamp;
        public ImageView ivUserImg;

        public ImageView ivGraphic;
        public TextView tvCaption;
        public TextView tvLikeCount;

        public PostViewHolder(View itemView) {
            super(itemView);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            ivUserImg = (ImageView) itemView.findViewById(R.id.ivUserImg);
            tvTimeStamp = (TextView) itemView.findViewById(R.id.tvTimeStamp);
            ivGraphic = (ImageView) itemView.findViewById(R.id.ivGraphic);
            tvCaption = (TextView) itemView.findViewById(R.id.tvCaption);
            tvLikeCount = (TextView) itemView.findViewById(R.id.tvLikesCount);
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

        //TODO: const for profile pic height width
        Picasso.with(holder.itemView.getContext())
                .load(post.user.profilePictureUrl)
                .resize(30, 30)
                .placeholder(R.drawable.gray_oval)
                .into(holder.ivGraphic);
        holder.tvUserName.setText(post.user.userName);
        holder.tvTimeStamp.setText(DateUtils.getRelativeTimeSpanString(post.createdTime * 1000));

        Picasso.with(holder.itemView.getContext())
                .load(post.image.imageUrl)
                .resize(post.image.imageWidth, 0)
                .placeholder(R.drawable.gray_rectangle)
                .into(holder.ivGraphic);

        holder.tvCaption.setText(post.caption);
        holder.tvLikeCount.setText(Utils.formatNumberForDisplay(post.likesCount));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
