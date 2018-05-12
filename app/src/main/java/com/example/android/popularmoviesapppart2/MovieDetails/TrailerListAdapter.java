package com.example.android.popularmoviesapppart2.MovieDetails;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviesapppart2.Model.Trailers;
import com.example.android.popularmoviesapppart2.R;
import com.example.android.popularmoviesapppart2.Utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrailerListAdapter extends RecyclerView.Adapter<TrailerListAdapter.TrailerViewHolder> {

    public final ArrayList<Trailers> mTrailersArrayList;
    public final OnTrailerClickListener mTrailerClicked;

    public TrailerListAdapter(ArrayList<Trailers> trailersArrayList, OnTrailerClickListener trailerClicked) {
        this.mTrailersArrayList = trailersArrayList;
        this.mTrailerClicked = trailerClicked;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_rv_list_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mTrailersArrayList.size();
    }

    /**
     * ViewHolder for Trailers
     */
    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View mView;
        TextView mTrailerName;
        ImageView mThumbNail;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mTrailerName = itemView.findViewById(R.id.tv_trailer_name);
            mThumbNail = itemView.findViewById(R.id.iv_trailer_image);
            mView.setOnClickListener(this);
        }

        void bind(int position) {
            mTrailerName.setText(mTrailersArrayList.get(position).getTrailerName());

            Uri youtubeImage = NetworkUtils.buildYoutubeThumbnailUri(mTrailersArrayList.get(position).getKey());
            Picasso.with(mView.getContext()).load(youtubeImage).into(mThumbNail);
        }

        @Override
        public void onClick(View v) {
            mTrailerClicked.onTrailerClick(getAdapterPosition());
        }
    }

    public interface OnTrailerClickListener {
        void onTrailerClick(int position);
    }
}
