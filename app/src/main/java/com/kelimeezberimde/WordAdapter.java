package com.kelimeezberimde;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.dift.ui.SwipeToAction;


public class WordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Words> items;

    public class WordViewHolder extends SwipeToAction.ViewHolder<Words> {
        public TextView wordView;
        public TextView meanView;
        public ImageView imageView;

        public WordViewHolder(View v) {
            super(v);
            wordView = (TextView) v.findViewById(R.id.title);
            meanView = (TextView) v.findViewById(R.id.author);
            imageView = (ImageView) v.findViewById(R.id.image);
        }
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);

        return new WordViewHolder(view);
    }

    public WordAdapter(List<Words> items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Words item = items.get(position);
        WordViewHolder vh = (WordViewHolder) holder;
        vh.wordView.setText(item.getWord());
        vh.meanView.setText(item.getMean());
        vh.imageView.setImageURI(Uri.parse(item.getImageUrl()));
        vh.data = item;
    }

}