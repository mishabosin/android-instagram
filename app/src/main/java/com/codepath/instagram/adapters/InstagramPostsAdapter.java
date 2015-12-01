package com.codepath.instagram.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
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

        renderAvatar(holder, post);
        holder.tvUserName.setText(post.user.userName);
        holder.tvTimeStamp.setText(DateUtils.getRelativeTimeSpanString(post.createdTime * 1000));

        renderPostImage(holder, post);
        holder.tvLikeCount.setText(Utils.formatNumberForDisplay(post.likesCount));

        renderCaption(holder, post);
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
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(
                context.getResources().getColor(R.color.blue_text));
        TypefaceSpan typefaceSpan = new TypefaceSpan("sans-serif-medium");

        SpannableStringBuilder ssb = new SpannableStringBuilder(post.user.userName);
        ssb.setSpan(foregroundColorSpan, 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(typefaceSpan, 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(" ");
        ssb.append(post.caption);
        holder.tvCaption.setText(ssb);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
