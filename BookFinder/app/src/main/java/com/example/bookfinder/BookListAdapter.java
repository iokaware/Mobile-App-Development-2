package com.example.bookfinder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookfinder.databinding.ItemBookBinding;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {

    private List<Book> bookList;

    public BookListAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookBinding binding = ItemBookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book currentBook = bookList.get(position);

        holder.binding.bookTitle.setText(currentBook.getTitle());
        holder.binding.bookAuthors.setText(currentBook.getAuthors());
        holder.binding.bookPublisher.setText(currentBook.getPublisher());
        holder.binding.bookPublishedDate.setText(currentBook.getPublishedDate());
        holder.binding.bookDescription.setText(currentBook.getDescription());

//        Log.v("ThumbnailUrl", currentBook.getThumbnailUrl());

        Glide.with(holder.itemView.getContext())
                .load(currentBook.getThumbnailUrl())
                .placeholder(R.drawable.default_book_thumbnail)
                .into(holder.binding.bookThumbnail);

//        new LoadImage(holder.binding.bookThumbnail).execute(currentBook.getThumbnailUrl());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemBookBinding binding;

        public ViewHolder(ItemBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}