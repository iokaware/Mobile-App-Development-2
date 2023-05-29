package com.example.firebasechatapp.adapter;

import static com.example.firebasechatapp.activity.SettingsActivity.PROFILE_PHOTO_BASE_URL;
import static com.example.firebasechatapp.activity.SettingsActivity.PROFILE_PHOTO_URL_EXTRA;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebasechatapp.R;
import com.example.firebasechatapp.activity.ChatActivity;
import com.example.firebasechatapp.activity.MainActivity;
import com.example.firebasechatapp.databinding.ItemUserBinding;
import com.example.firebasechatapp.model.Message;
import com.example.firebasechatapp.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context context;

    private String userId;
    private List<User> userList;
    private MainActivity.UserItemListener listener;

    public UserAdapter(Context context, String userId, List<User> userList, MainActivity.UserItemListener listener) {
        this.context = context;
        this.userId = userId;
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserBinding binding = ItemUserBinding.inflate(
                LayoutInflater.from(context), parent, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.binding.textName.setText(user.getName());

        Glide.with(context)
                .load(PROFILE_PHOTO_BASE_URL
                        + user.getId()
                        + PROFILE_PHOTO_URL_EXTRA)
                .placeholder(R.drawable.account_icon)
                .error(R.drawable.account_icon)
                .into(holder.binding.imageProfile);

        if (position == (userList.size() - 1)) {
            holder.binding.view.setVisibility(View.GONE);
        }

        holder.binding.constraintUser
                .setOnClickListener(view -> {
            listener.onItemLongClickListener(user.getId(), user.getName()
                    );
        });
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        ItemUserBinding binding;

        public UserViewHolder(ItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
