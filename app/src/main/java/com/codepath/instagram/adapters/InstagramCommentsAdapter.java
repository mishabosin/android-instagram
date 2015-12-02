package com.codepath.instagram.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramComment;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class InstagramCommentsAdapter extends
        RecyclerView.Adapter<InstagramCommentsAdapter.CommentViewHolder> {

    private List<InstagramComment> comments;

    public InstagramCommentsAdapter(List<InstagramComment> comments) {
        this.comments = comments;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCommentText;
        public TextView tvCommentDate;
        public ImageView ivCommentAvatar;

        public CommentViewHolder(View itemView) {
            super(itemView);
            tvCommentText = (TextView) itemView.findViewById(R.id.tvCommentText);
            ivCommentAvatar = (ImageView) itemView.findViewById(R.id.ivCommentAvatar);
            tvCommentDate = (TextView) itemView.findViewById(R.id.tvCommentDate);
        }
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_item_comment, parent, false);

        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        InstagramComment comment = comments.get(position);

        Context context = holder.itemView.getContext();
        renderAvatar(holder, comment);
        holder.tvCommentText.setText(Utils.formatUserAndText(context, comment.user.userName, comment.text));
        holder.tvCommentDate.setText(DateUtils.getRelativeTimeSpanString(comment.createdTime * 1000));
    }

    private void renderAvatar(CommentViewHolder holder, InstagramComment comment) {
        int radius = R.dimen.comment_avatar_size / 2;
        Transformation makeCircle = new RoundedTransformationBuilder()
                .cornerRadiusDp(radius)
                .oval(true)
                .build();

        Picasso.with(holder.itemView.getContext())
                .load(comment.user.profilePictureUrl)
                .fit()
                .placeholder(R.drawable.gray_oval)
                .transform(makeCircle)
                .into(holder.ivCommentAvatar);
    }


    @Override
    public int getItemCount() {
        return comments.size();
    }
}
