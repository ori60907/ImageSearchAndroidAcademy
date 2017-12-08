package com.fundamentals.academy.ori.imagesearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 08/12/2017.
 */

public class ImageFeedAdapter extends RecyclerView.Adapter<ImageFeedAdapter.ViewHolder>{

    private ArrayList<ImageResult> imagesData;
    private Context context;

    public ImageFeedAdapter(ArrayList<ImageResult> imagesData, Context context){
        this.imagesData = imagesData;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new ViewHolder(inflater.inflate(R.layout.image_layout, parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageResult imageResult = imagesData.get(position);
        Picasso.with(context).load(imageResult.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imagesData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.single_image);
        }
    }

    public void updateImagesFromHitsList(List<Hit> hitsList){
        imagesData.clear();

        for (int i=0; i<hitsList.size(); i++){
            imagesData.add(new ImageResult(hitsList.get(i).getWebformatURL()));
        }

        notifyDataSetChanged();
    }
}
