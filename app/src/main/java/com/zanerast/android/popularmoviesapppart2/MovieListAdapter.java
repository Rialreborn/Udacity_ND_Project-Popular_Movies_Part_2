package com.zanerast.android.popularmoviesapppart2;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zanerast.android.popularmoviesapppart2.Model.Movie;
import com.zanerast.android.popularmoviesapppart2.Utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieHolder> {

    private ArrayList<Movie> mMovieArray;
    private final OnItemClickListener mListener;

    public MovieListAdapter(ArrayList<Movie> movieObjects, OnItemClickListener listener) {
        this.mMovieArray = movieObjects;
        this.mListener = listener;
    }

    public void  notifyChange(ArrayList<Movie> newMovieArray) {
        this.mMovieArray = newMovieArray;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_poster_rv_item, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mMovieArray.size();
    }



    public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final ImageView mMoviePosterImage;


        public MovieHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mMoviePosterImage = itemView.findViewById(R.id.rv_movie_list_image);
            mView.setOnClickListener(this);
        }

        void bind(int position) {
            String imageUrl = mMovieArray.get(position).getImageUrl();
            if (imageUrl == null || imageUrl.isEmpty()) {
                mMoviePosterImage.setImageBitmap(mMovieArray.get(position).getMoviePoster());
            } else {
                Uri image = NetworkUtils.buildImageURL(imageUrl);
                Picasso.with(mView.getContext())
                        .load(image)
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.failed_load)
                        .into(mMoviePosterImage);
            }
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mListener.onItemClick(position);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
