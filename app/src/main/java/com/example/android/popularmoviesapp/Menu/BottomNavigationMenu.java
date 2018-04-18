package com.example.android.popularmoviesapp.Menu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popularmoviesapp.MovieListFragment;
import com.example.android.popularmoviesapp.R;
import com.example.android.popularmoviesapp.Utils.Constants;

public class BottomNavigationMenu extends Fragment {

    private MenuItem menuItem;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final FragmentManager fragmentManager = getFragmentManager();

        View rootView = inflater.inflate(R.layout.bottom_menu_layout, container, false);

        BottomNavigationView bottomNavigationView = rootView.findViewById(R.id.bottom_navigation_menu);
        final Menu menu = bottomNavigationView.getMenu();

        // Set up Pref
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String currentSortOrder = sharedPreferences.getString(Constants.MOVIE_SORT_ORDER_KEY, Constants.MOVIE_SORT_ORDER_POPULAR);
        if (currentSortOrder.equals(Constants.MOVIE_SORT_ORDER_RATING)) {
            menuItem = menu.getItem(1);
            int id = menuItem.getItemId();
            bottomNavigationView.setSelectedItemId(id);
        }

        // Create Pref Editor
        final SharedPreferences.Editor editor = sharedPreferences.edit();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.popular_movies_menu_item:
                        menuItem = menu.getItem(0);
                        editor.putString(Constants.MOVIE_SORT_ORDER_KEY, Constants.MOVIE_SORT_ORDER_POPULAR).apply();
                        replaceFragment(fragmentManager);
                        break;

                    case R.id.highest_rated_movies_menu_item:
                        menuItem = menu.getItem(1);
                        editor.putString(Constants.MOVIE_SORT_ORDER_KEY, Constants.MOVIE_SORT_ORDER_RATING).apply();
                        replaceFragment(fragmentManager);
                        break;
                }
                menuItem.setChecked(true);

                return false;
                }
            });

        return rootView;
    }

    private void replaceFragment(FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.movie_frame_layout, new MovieListFragment())
                    .commit();
        }
    }
}
