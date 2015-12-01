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
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

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

        int radius = R.dimen.avatar_size / 2;
        Transformation makeCircle = new RoundedTransformationBuilder()
                .cornerRadiusDp(radius)
                .oval(true)
                .build();

        Picasso.with(holder.itemView.getContext())
                .load(post.user.profilePictureUrl)
                .fit()
                .placeholder(R.drawable.gray_oval)
                .transform(makeCircle)
                .into(holder.ivUserImg);
        holder.tvUserName.setText(post.user.userName);
        holder.tvTimeStamp.setText(DateUtils.getRelativeTimeSpanString(post.createdTime * 1000));

        Picasso.with(holder.itemView.getContext())
                .load(post.image.imageUrl)
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
