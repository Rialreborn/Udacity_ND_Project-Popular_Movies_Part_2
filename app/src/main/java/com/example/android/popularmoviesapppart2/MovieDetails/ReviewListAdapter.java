package com.example.android.popularmoviesapppart2.MovieDetails;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmoviesapppart2.Model.Reviews;
import com.example.android.popularmoviesapppart2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zane on 12/05/2018.
 */

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewViewHolder>{

    ArrayList<Reviews> mReviewArray;
    OnReviewClickListener mOnReviewClickListener;

    public ReviewListAdapter(ArrayList<Reviews> reviewArray, OnReviewClickListener onReviewClickListener) {
        this.mReviewArray = reviewArray;
        this.mOnReviewClickListener = onReviewClickListener;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.review_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mReviewArray.size();}


    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvAuthor;
        TextView tvContent;
        FloatingActionButton imExpandReview;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvContent = itemView.findViewById(R.id.tv_review_content);
            imExpandReview = itemView.findViewById(R.id.floatingActionButton);

            imExpandReview.setOnClickListener(this);


        }

        void bind(int position) {
            tvAuthor.setText(mReviewArray.get(position).getAuthor());
            String content = mReviewArray.get(position).getContent();
            tvContent.setText(content.trim());
        }

        @Override
        public void onClick(View v) {
            mOnReviewClickListener.onReviewClicked(tvContent, imExpandReview);
        }

    }

    interface OnReviewClickListener {
        void onReviewClicked(TextView reviewContent, FloatingActionButton imageButton);
    }

}
