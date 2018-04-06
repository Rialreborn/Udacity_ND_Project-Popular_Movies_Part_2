package com.example.zane.popularmoviesapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Zane on 05/04/2018.
 */

public class BottomNavigationMenu extends Fragment {

    private MenuItem menuItem;

    public static final String MOVIE_SORT_ORDER_KEY = "movie_sort_order";
    public static final String MOVIE_SORT_ORDER_POPULAR = "popular";
    private static final String MOVIE_SORT_ORDER_RATING = "top_rated";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.bottom_menu_layout, container, false);

        BottomNavigationView bottomNavigationView = rootView.findViewById(R.id.bottom_navigation_menu);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        final Menu menu = bottomNavigationView.getMenu();

        // Set up Pref
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String currentSortOrder = sharedPreferences.getString(MOVIE_SORT_ORDER_KEY, MOVIE_SORT_ORDER_POPULAR);
        if (currentSortOrder.equals(MOVIE_SORT_ORDER_RATING)) {
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
                        editor.putString(MOVIE_SORT_ORDER_KEY, MOVIE_SORT_ORDER_POPULAR).apply();
                        break;

                    case R.id.highest_rated_movies_menu_item:
                        menuItem = menu.getItem(1);
                        editor.putString(MOVIE_SORT_ORDER_KEY, MOVIE_SORT_ORDER_RATING).apply();
                        break;
                }
                menuItem.setChecked(true);

                return false;
                }
            });

        return rootView;
    }
}
