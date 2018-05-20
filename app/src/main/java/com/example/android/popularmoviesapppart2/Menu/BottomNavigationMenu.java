package com.example.android.popularmoviesapppart2.Menu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.example.android.popularmoviesapppart2.Database.MovieContract;
import com.example.android.popularmoviesapppart2.MovieListFragment;
import com.example.android.popularmoviesapppart2.R;
import com.example.android.popularmoviesapppart2.Utils.Constants;

import java.lang.reflect.Field;

public class BottomNavigationMenu extends Fragment {

    private MenuItem menuItem;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final FragmentManager fragmentManager = getFragmentManager();

        View rootView = inflater.inflate(R.layout.bottom_menu_layout, container, false);

        BottomNavigationView bottomNavigationView = rootView.findViewById(R.id.bottom_navigation_menu);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        final Menu menu = bottomNavigationView.getMenu();

        // Set up Pref
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String currentSortOrder = sharedPreferences.getString(Constants.MOVIE_SORT_ORDER_KEY, Constants.MOVIE_SORT_ORDER_POPULAR);

        int id;
        switch (currentSortOrder) {
            case Constants.MOVIE_SORT_ORDER_POPULAR:
                menuItem = menu.getItem(0);
                id = menuItem.getItemId();
                break;

            case Constants.MOVIE_SORT_ORDER_RATING:
                menuItem = menu.getItem(1);
                id = menuItem.getItemId();
                break;

            default:
                menuItem = menu.getItem(2);
                id = menuItem.getItemId();
                break;
        }
        bottomNavigationView.setSelectedItemId(id);

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
                        menuItem.setChecked(true);
                        break;

                    case R.id.highest_rated_movies_menu_item:
                        menuItem = menu.getItem(1);
                        editor.putString(Constants.MOVIE_SORT_ORDER_KEY, Constants.MOVIE_SORT_ORDER_RATING).apply();
                        replaceFragment(fragmentManager);
                        menuItem.setChecked(true);
                        break;

                    case R.id.favourite_movies_menu_item:
                        menuItem = menu.getItem(2);
                        editor.putString(Constants.MOVIE_SORT_ORDER_KEY, Constants.MOVIE_SORT_ORDER_FAVOURITES).apply();
                        replaceFragment(fragmentManager);
                        menuItem.setChecked(true);
                        break;

                    case R.id.wipe_favourites_menu_item:
                        getContext().getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI, null, null);
                        replaceFragment(fragmentManager);
                        break;

                }


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
