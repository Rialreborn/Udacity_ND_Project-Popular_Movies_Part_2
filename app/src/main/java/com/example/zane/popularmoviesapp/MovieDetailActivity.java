package com.example.zane.popularmoviesapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Zane on 08/04/2018.
 */

public class MovieDetailActivity extends Fragment {

    public MovieDetailActivity() {}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_detail, container, false);
    }
}
