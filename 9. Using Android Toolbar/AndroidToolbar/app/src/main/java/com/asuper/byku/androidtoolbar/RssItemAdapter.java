package com.asuper.byku.androidtoolbar;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import java.util.List;
import java.util.Random;
/**
 * Created by Byku on 14.06.2016.
 */

//6. Exercise: Using the contextual action mode
public class RssItemAdapter extends RecyclerView.Adapter<RssItemAdapter.ViewHolder>{

    private List<RssItem> rssItems;
    private MyListFragment myListFragment;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType0){
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout,parent,false);
        return new ViewHolder(v);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public View mainLayout;
        public TextView txtHeader;
        public TextView txtFooter;
        public ImageView imageView;

        public ViewHolder(View v){
            super(v);
            mainLayout = v;
            txtHeader = (TextView) v.findViewById(R.id.rsstitle);
            txtFooter=(TextView) v.findViewById(R.id.rssurl);
            imageView = (ImageView) v.findViewById(R.id.icon);
        }
    }

    @Override void onBindViewHolder(final ViewHolder holder, final int position){
        final RssItem rssItem = rssItems.get(position);
        holder.txtHeader.setText(rssItem.getTitle());
        holder.txtFooter.setText(rssItem.getLink());
        holder.mainLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                myListFragment.updateDetail(rssItem.getLink());
            }
        });
        holder.mainLayout.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v){
                myListFragment.goToActionMode(rssItem);
                return true;
            }
        });
    }

    @Override
    public int getItemCount(){
        return rssItems.size();
    }

    public RssItemadapter(List<RssItem> rssItems, MyListFragment myListFragment){
        this.rssItems = rssItems;
        this.myListFragment = myListFragment;
    }
}
//*/