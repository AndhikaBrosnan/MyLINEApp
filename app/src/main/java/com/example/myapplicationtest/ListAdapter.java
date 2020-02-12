package com.example.myapplicationtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationtest.Model.Profile;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder_>{
    private Context mContext;
    private ArrayList<Profile> mData;

    @Override
    public ViewHolder_ onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.list_item,parent,false
        );
        return new ViewHolder_(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder_ holder, int position) {
        Profile currentProfile = mData.get(position);

        holder.mName.setText(currentProfile.getName());
        holder.mStat.setText(currentProfile.getStatus());
        Picasso.get().load(currentProfile.getAvatar());
//        holder.mGambar.setText(currentProfile.getAvatar());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public ListAdapter(Context mContext, ArrayList<Profile> itemList) {
        this.mContext = mContext;
        mData = itemList;
    }


    public class ViewHolder_ extends RecyclerView.ViewHolder{
        final TextView mName;
        final TextView mStat;
        final CircleImageView mGambar;

        public ViewHolder_(View itemView) {
            super(itemView);
            mName = (TextView)itemView.findViewById(R.id.textViewName);
            mStat = (TextView)itemView.findViewById(R.id.textViewStat);
            mGambar = (CircleImageView)itemView.findViewById(R.id.profile_image);

        }

    }
}
