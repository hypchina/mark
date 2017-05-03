package com.jgacq.mark.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgacq.mark.BookMarkDetailsActivity;
import com.jgacq.mark.R;
import com.jgacq.mark.litepal.Bookmark;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/4/29.
 */
public class MarkListAdapter extends RecyclerView.Adapter<MarkListAdapter.ViewHolder>{
    private List<Bookmark> bookmarks;
    private Random random = new Random();
    private Context c;

    public MarkListAdapter(List<Bookmark> marks){
        bookmarks = marks;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        CardView cardView = (CardView)LayoutInflater.from(parent.getContext()).inflate(R.layout.mark_card,parent,false);

        final ViewHolder viewHolder = new ViewHolder(cardView);
        if(c == null){
            c = parent.getContext();
        }
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                String url = bookmarks.get(position).getSiteUrl();
                Intent intent = new Intent(c, BookMarkDetailsActivity.class);
                intent.putExtra("url",url);
                c.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Bookmark bookmark = bookmarks.get(position);
        int num = random.nextInt(8) + 1;
        int imageRid = c.getResources().getIdentifier("mark_img_0"+num,"drawable", c.getPackageName());
        String bookMarkName = bookmark.getSiteName();
        holder.imageView.setBackgroundResource(imageRid);
        holder.textView.setText(bookMarkName);
    }

    @Override
    public int getItemCount() {
        return bookmarks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.item_mark_img);
            textView = (TextView)itemView.findViewById(R.id.item_mark_text);
        }
    }

}
