package com.topsportsnews.app.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.topsportsnews.activities.R;
import com.topsportsnews.app.activities.DetailActivity;
import com.topsportsnews.app.models.SportsItem;


import java.util.List;

public class SportsAdapter extends RecyclerView.Adapter<SportsAdapter.CustomViewHolder> {
    private List<SportsItem> sportsItemList;
    private Context mContext;

    public SportsAdapter(Context context, List<SportsItem> sportsItemList) {
        this.sportsItemList = sportsItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        SportsItem sportsItem = sportsItemList.get(i);

        //Download image using picasso library
        Picasso.with(mContext).load(sportsItem.getThumbnail())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(customViewHolder.imageView);

        //Setting text view title
        customViewHolder.textView.setText(Html.fromHtml(sportsItem.getTitle()));
        customViewHolder.description.setText(Html.fromHtml(sportsItem.getDescription()));
        //Handle click event on both title and image click
        customViewHolder.textView.setOnClickListener(clickListener);
        customViewHolder.imageView.setOnClickListener(clickListener);
        customViewHolder.description.setOnClickListener(clickListener);

        customViewHolder.textView.setTag(customViewHolder);
        customViewHolder.imageView.setTag(customViewHolder);
        customViewHolder.description.setTag(customViewHolder);


    }

    @Override
    public int getItemCount() {
        return (null != sportsItemList ? sportsItemList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;
        protected TextView description;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);
            this.description = (TextView) view.findViewById(R.id.description);
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CustomViewHolder holder = (CustomViewHolder) view.getTag();
            int position = holder.getPosition();
            SportsItem sportsItem = sportsItemList.get(position);
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra("sportsItem",  sportsItem);
            mContext.startActivity(intent);
//            Toast.makeText(mContext, sportsItem.getTitle(), Toast.LENGTH_SHORT).show();
        }
    };
}


