package com.codepath.instagram.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.codepath.instagram.models.InstagramUser;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by misha on 12/2/15.
 */
public class SearchUserResultsAdapter extends
        RecyclerView.Adapter<SearchUserResultsAdapter.SearchUserViewHolder> {

    List<InstagramUser> users;

    public SearchUserResultsAdapter(List<InstagramUser> users) {
        this.users = users;
    }

    public static class SearchUserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserName;
        public TextView tvUserFullName;
        public ImageView ivUserAvatar;

        public SearchUserViewHolder(View itemView) {
            super(itemView);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvUserFullName = (TextView) itemView.findViewById(R.id.tvUserFullName);
            ivUserAvatar = (ImageView) itemView.findViewById(R.id.ivUserAvatar);
        }
    }

    @Override
    public SearchUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_item_user, parent, false);

        return new SearchUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchUserViewHolder holder, int position) {
        InstagramUser user = users.get(position);

        renderAvatar(holder, user);
        holder.tvUserName.setText(user.userName);
        holder.tvUserFullName.setText(user.fullName);
    }

    private void renderAvatar(SearchUserViewHolder holder, InstagramUser user) {
        int radius = R.dimen.comment_avatar_size / 2;
        Transformation makeCircle = new RoundedTransformationBuilder()
                .cornerRadiusDp(radius)
                .oval(true)
                .build();

        Picasso.with(holder.itemView.getContext())
                .load(user.profilePictureUrl)
                .fit()
                .placeholder(R.drawable.gray_oval)
                .transform(makeCircle)
                .into(holder.ivUserAvatar);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
