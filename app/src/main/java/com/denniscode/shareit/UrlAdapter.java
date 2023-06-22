package com.denniscode.shareit;

import java.util.ArrayList;
import android.widget.TextView;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UrlAdapter extends RecyclerView.Adapter<UrlAdapter.UrlViewHolder> {

    private ArrayList<String> urls;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;

    public UrlAdapter(ArrayList<String> urls) {
        this.urls = urls;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        itemLongClickListener = listener;
    }

    public static class UrlViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewUrl;

        public UrlViewHolder(View itemView, final OnItemClickListener listener, final OnItemLongClickListener longClickListener) {
            super(itemView);
            textViewUrl = itemView.findViewById(R.id.text_view_url);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (longClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            longClickListener.onItemLongClick(position);
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }

    @NonNull
    @Override
    public UrlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_url, parent, false);

        return new UrlViewHolder(view, itemClickListener, itemLongClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UrlViewHolder holder, int position) {
        String url = urls.get(position);
        holder.textViewUrl.setText(url);
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }
}