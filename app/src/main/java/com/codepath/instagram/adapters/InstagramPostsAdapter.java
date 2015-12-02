package com.codepath.instagram.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.listeners.OnAllCommentsClickListener;
import com.codepath.instagram.listeners.OnDotsClickListener;
import com.codepath.instagram.models.InstagramComment;
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

    // Define listener member variable
    private static OnAllCommentsClickListener commentsListener;
    private static OnDotsClickListener shareClickListener;

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnAllCommentsClickListener(OnAllCommentsClickListener listener) {
        this.commentsListener = listener;
    }

    public void setOnShareClickListener(OnDotsClickListener listener) {
        this.shareClickListener = listener;
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserName;
        public TextView tvTimeStamp;
        public ImageView ivUserImg;

        public ImageView ivGraphic;
        public TextView tvCaption;
        public TextView tvLikeCount;

        public TextView tvAllComments;
        public LinearLayout llComments;
        public ImageView ivDots;

        public PostViewHolder(final View itemView) {
            super(itemView);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            ivUserImg = (ImageView) itemView.findViewById(R.id.ivUserImg);
            tvTimeStamp = (TextView) itemView.findViewById(R.id.tvTimeStamp);
            ivGraphic = (ImageView) itemView.findViewById(R.id.ivGraphic);
            tvCaption = (TextView) itemView.findViewById(R.id.tvCaption);
            tvLikeCount = (TextView) itemView.findViewById(R.id.tvLikesCount);
            llComments = (LinearLayout) itemView.findViewById(R.id.llComments);
            tvAllComments = (TextView) itemView.findViewById(R.id.tvAllComments);

            tvAllComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (commentsListener != null) {
                        commentsListener.onCommentsClick(getLayoutPosition());
                    }
                }
            });

            ivDots = (ImageView) itemView.findViewById(R.id.ivDots);
            ivDots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (shareClickListener != null) {
                        shareClickListener.onClick(ivDots, itemView);
                    }
                }
            });
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

        renderAvatar(holder, post);
        holder.tvUserName.setText(post.user.userName);
        holder.tvTimeStamp.setText(DateUtils.getRelativeTimeSpanString(post.createdTime * 1000));

        renderPostImage(holder, post);
        holder.tvLikeCount.setText(Utils.formatNumberForDisplay(post.likesCount));

        renderCaption(holder, post);
        renderComments(holder, post);
    }

    private void renderAvatar(PostViewHolder holder, InstagramPost post) {
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
    }

    private void renderPostImage(PostViewHolder holder, InstagramPost post) {
        Picasso.with(holder.itemView.getContext())
                .load(post.image.imageUrl)
                .placeholder(R.drawable.gray_rectangle)
                .into(holder.ivGraphic);
    }

    private void renderCaption(PostViewHolder holder, InstagramPost post) {
        if (post.caption == null || post.caption.equals("")) {
            holder.tvCaption.setVisibility(View.GONE);
            return;
        }

        Context context = holder.itemView.getContext();
        holder.tvCaption.setText(Utils.formatUserAndText(context, post.user.userName, post.caption));
    }

    private void renderComments(PostViewHolder holder, InstagramPost post) {
        holder.llComments.removeAllViews();

        if (post.comments == null || post.comments.size() == 0) {
            holder.llComments.setVisibility(View.GONE);
            return;
        }

        renderCommentSummary(holder, post);

        for (int i=0; i < 2 && i < post.comments.size(); i++) {
            InstagramComment comment = post.comments.get(i);

            View commentView = LayoutInflater
                    .from(holder.itemView.getContext())
                    .inflate(R.layout.layout_item_text_comment, holder.llComments, false);
            TextView tvComment = (TextView) commentView.findViewById(R.id.tvComment);
            Context context = holder.itemView.getContext();
            tvComment.setText(Utils.formatUserAndText(context, comment.user.userName, comment.text));

            holder.llComments.addView(commentView);
        }
    }

    private void renderCommentSummary(PostViewHolder holder, InstagramPost post) {
        if (post.commentsCount < 3) {
            holder.tvAllComments.setVisibility(View.GONE);
            return;
        }
        Context context = holder.itemView.getContext();
        String summary = context.getString(R.string.view_all);
        summary += ' ';
        summary += String.valueOf(post.commentsCount);
        summary += ' ';
        summary += context.getString(R.string.comments);
        holder.tvAllComments.setText(summary);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
