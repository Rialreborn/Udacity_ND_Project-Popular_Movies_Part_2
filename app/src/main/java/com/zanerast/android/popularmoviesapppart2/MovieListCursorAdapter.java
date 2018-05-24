package com.zanerast.android.popularmoviesapppart2;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zanerast.android.popularmoviesapppart2.Database.MovieContract;

/**
 * Created by Zane on 19/05/2018.
 */

public class MovieListCursorAdapter extends RecyclerView.Adapter<MovieListCursorAdapter.MovieViewHolder>{

    private Cursor mCursor;
    private Context mContext;


    public MovieListCursorAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_poster_rv_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public Cursor swapCursor(Cursor newCursor) {

        if (mCursor == newCursor) {
            return null;
        }
        Cursor tempCursor = mCursor;
        this.mCursor = newCursor;

        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
        return tempCursor;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final ImageView mMoviePosterImage;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mMoviePosterImage = itemView.findViewById(R.id.rv_movie_list_image);
            mView.setOnClickListener(this);
        }

        void bind(int position) {
            mCursor.moveToPosition(position);

            int img = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_IMAGE);

            byte[] blob = mCursor.getBlob(img);
            Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);

            mMoviePosterImage.setImageBitmap(bmp);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
//            mListener.onItemClick(position);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
