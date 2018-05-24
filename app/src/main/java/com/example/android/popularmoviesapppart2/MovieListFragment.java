package com.example.android.popularmoviesapppart2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmoviesapppart2.Database.MovieContract.MovieEntry;
import com.example.android.popularmoviesapppart2.Model.Movie;
import com.example.android.popularmoviesapppart2.MovieDetails.MovieDetailActivity;
import com.example.android.popularmoviesapppart2.Utils.Constants;
import com.example.android.popularmoviesapppart2.Utils.EndlessScrollListener;
import com.example.android.popularmoviesapppart2.Utils.LoadMovieData;
import com.example.android.popularmoviesapppart2.Utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class MovieListFragment extends Fragment
        implements
        LoadMovieData.BeforeTaskCompleteInterface,
        LoadMovieData.AfterTaskCompleteInterface

{


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.error_message_tv)
    TextView tvErrorMessage;

    private String mMovieOrder;
    private final String INSTANT_STATE_LAYOUT = "saved_layout";
    private final String INSTANT_STATE_MOVIE_ARRAY = "saved_array";
    private Parcelable mSavedRecyclerLayoutState;
    private EndlessScrollListener scrollListener;
    GridLayoutManager mLayoutManager;
    int page;
    ArrayList<Movie> mMovieArrayList;
    MovieListAdapter movieListAdapter;

    public MovieListFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        mMovieOrder = sharedPreferences.getString(Constants.MOVIE_SORT_ORDER_KEY, Constants.MOVIE_SORT_ORDER_POPULAR);


        final View view = inflater.inflate(R.layout.movie_list_fragment, container, false);
        ButterKnife.bind(MovieListFragment.this, view);

        recyclerView.setHasFixedSize(true);
        mLayoutManager = setLayoutManager(container);
        recyclerView.setLayoutManager(mLayoutManager);
        page = 1;

        if (!mMovieOrder.equals(Constants.MOVIE_SORT_ORDER_FAVOURITES)) {
            if (Constants.API_KEY.isEmpty()) {
                noApiKeyFound();
            } else if (!networkConnection()) {
                noNetworkFound();
            }
            loadMovieData();
        } else {
            loadFavourites();
        }

        scrollListener = new EndlessScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadMovieData();
            }
        };

        recyclerView.addOnScrollListener(scrollListener);

        return view;

    }

    private void loadMovieData() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo info = null;
        if (connectivityManager != null) {
            info = connectivityManager.getActiveNetworkInfo();
        }

        if (info != null && info.isConnectedOrConnecting()) {
            URL url = NetworkUtils.buildMoviesUrl(mMovieOrder, mMovieArrayList);
            new LoadMovieData(mMovieArrayList, this, this).execute(url);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(INSTANT_STATE_LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            mSavedRecyclerLayoutState = savedInstanceState.getParcelable(INSTANT_STATE_LAYOUT);
            recyclerView.getLayoutManager().onRestoreInstanceState(mSavedRecyclerLayoutState);
        }
    }

    private void loadFavourites() {
        ArrayList<Movie> moviesArrayList = new ArrayList<>();

        Cursor cursor = getContext().getContentResolver().query(
                MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                moviesArrayList.add(new Movie(
                        cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_PLOT)),
                        cursor.getDouble(cursor.getColumnIndex(MovieEntry.COLUMN_USER_RATING)),
                        cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_RELEASE_DATE)),
                        cursor.getInt(cursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_ID)),
                        cursor.getBlob(cursor.getColumnIndex(MovieEntry.COLUMN_POSTER_IMAGE)),
                        cursor.getBlob(cursor.getColumnIndex(MovieEntry.COLUMN_BACKDROP_IMAGE))));
            } while (cursor.moveToNext());
        }

        if (moviesArrayList.size() < 1) {
            recyclerView.setVisibility(View.GONE);
            tvErrorMessage.setText(getString(R.string.no_favourites));
        }

        setAdapter(moviesArrayList);
    }


    @SuppressWarnings("ConstantConditions")
    private GridLayoutManager setLayoutManager(ViewGroup container) {
        if (container != null) {
            container.removeAllViews();
        }

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return new GridLayoutManager(container.getContext(), 5);
        } else {
            return new GridLayoutManager(container.getContext(), 3);
        }
    }


    /**
     * Override method for LoadMovieData, before Async has run
     * Show Progress Bar
     */
    //
    @Override
    public void beforeTaskComplete() {
        if (recyclerView.getChildCount() < 1) {
            recyclerView.setVisibility(View.GONE);
        }
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Override method for LoadMovieData, after AsyncTask has completed
     * Hide Progress Bar
     * Set Adapter to RV
     *
     * @param result - The ArrayList<Movie> filled with data after AsyncTask has run
     */
    @Override
    public void afterTaskComplete(final ArrayList<Movie> result) {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        if (result.size() <= 20) {
            setAdapter(result);
        } else {
            movieListAdapter.notifyChange(result);
        }
    }

    private void setAdapter(final ArrayList<Movie> result) {
        mMovieArrayList = result;
        movieListAdapter = new MovieListAdapter(recyclerView, mMovieArrayList, new MovieListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Movie movie = result.get(position);
                Intent intent = new Intent(getContext(), MovieDetailActivity.class);
                intent.putExtra(Constants.INTENT_MOVIE_OBJECT, movie);
                startActivity(intent);
            }
        });

            recyclerView.setAdapter(movieListAdapter);
    }

    private void noNetworkFound() {
        recyclerView.setVisibility(View.GONE);
        tvErrorMessage.setVisibility(View.VISIBLE);
        tvErrorMessage.setText(getString(R.string.no_network));
    }

    private void noApiKeyFound() {
        recyclerView.setVisibility(View.GONE);
        tvErrorMessage.setVisibility(View.VISIBLE);
        tvErrorMessage.setText(getString(R.string.api_key_needed));
    }

    private boolean networkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo info = null;
        if (connectivityManager != null) {
            info = connectivityManager.getActiveNetworkInfo();
        }

        return info != null && info.isConnectedOrConnecting();
    }

}



