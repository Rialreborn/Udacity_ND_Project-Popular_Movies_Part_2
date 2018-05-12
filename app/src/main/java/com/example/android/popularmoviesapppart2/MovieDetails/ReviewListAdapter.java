package com.example.android.popularmoviesapppart2.MovieDetails;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmoviesapppart2.Model.Reviews;
import com.example.android.popularmoviesapppart2.R;

import java.util.ArrayList;

/**
 * Created by Zane on 12/05/2018.
 */

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewViewHolder>{

    ArrayList<Reviews> mReviewArray;

    public ReviewListAdapter(ArrayList<Reviews> reviewArray) {
        this.mReviewArray = reviewArray;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.review_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mReviewArray.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView tvAuthor;
        TextView tvContent;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvContent = itemView.findViewById(R.id.tv_review_content);
        }

        void bind(int position) {
            tvAuthor.setText(mReviewArray.get(position).getAuthor());
            tvContent.setText(mReviewArray.get(position).getContent());


        }
    }

}
