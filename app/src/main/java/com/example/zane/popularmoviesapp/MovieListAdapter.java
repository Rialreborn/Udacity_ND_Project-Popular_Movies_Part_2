package com.example.zane.popularmoviesapp;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zane.popularmoviesapp.Model.Movie;
import com.example.zane.popularmoviesapp.Utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Zane on 03/04/2018.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieHolder> {

    private ArrayList<Movie> mMovieArray;
    private OnItemClickListener mListener;



    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public MovieListAdapter(ArrayList<Movie> movieObjects, OnItemClickListener listener) {
        mMovieArray = movieObjects;
        mListener = listener;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_poster_fragment, parent, false);
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

    public class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final View mView;
        public final ImageView mMoviePosterImage;

        public MovieHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mMoviePosterImage = itemView.findViewById(R.id.rv_movie_list_image);
            mView.setOnClickListener(this);
        }

        void bind(int position) {
            String imageUrl = mMovieArray.get(position).getImageUrl().toString();
            Uri image = NetworkUtils.buildImageURL(imageUrl);

            Picasso.with(mView.getContext()).load(image).into(mMoviePosterImage);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mListener.onItemClick(itemView, position);
        }
    }
}
