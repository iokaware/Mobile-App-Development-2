package com.example.firebasechatapp.adapter;

import static com.example.firebasechatapp.activity.SettingsActivity.PROFILE_PHOTO_BASE_URL;
import static com.example.firebasechatapp.activity.SettingsActivity.PROFILE_PHOTO_URL_EXTRA;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebasechatapp.R;
import com.example.firebasechatapp.activity.ChatActivity;
import com.example.firebasechatapp.databinding.ItemMessageBinding;
import com.example.firebasechatapp.model.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private Context context;
    private String userId;
    private List<Message> messageList;
    private ChatActivity.MessageItemListener listener;

    public MessageAdapter(Context context, String userId, List<Message> messageList, ChatActivity.MessageItemListener listener) {
        this.context = context;
        this.userId = userId;
        this.messageList = messageList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMessageBinding binding = ItemMessageBinding.inflate(
                LayoutInflater.from(context), parent, false);
        return new MessageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.binding.messageTextView.setText(message.getText());
//        holder.binding.messengerTextView.setText(message.getUserId_receiverId());
        holder.binding.messageTime.setText(message.getTime());

        Glide.with(context)
                .load(PROFILE_PHOTO_BASE_URL
                        + message.getUserId()
                        + PROFILE_PHOTO_URL_EXTRA)
                .placeholder(R.drawable.account_icon)
                .error(R.drawable.account_icon)
                .into(holder.binding.messengerImageView);

        if (!message.getUserId().equals(userId)) {
            holder.binding.messageTextView.setBackgroundResource(
                    R.drawable.rounded_message_blue);
            holder.binding.messageTextView.setTextColor(Color.WHITE);
        } else if (message.getUserId().equals(userId)) {
            holder.binding.messageTextView.setBackgroundResource(
                    R.drawable.rounded_message_gray);
            holder.binding.messageTextView.setTextColor(Color.BLACK);
        }

        holder.binding.messageTextView.setOnLongClickListener(view -> {
            listener.onItemLongClickListener(position, message.getId());
            return true;
        });
    }


    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        ItemMessageBinding binding;

        public MessageViewHolder(ItemMessageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
