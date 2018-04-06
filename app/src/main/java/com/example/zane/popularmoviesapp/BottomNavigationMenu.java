package com.example.zane.popularmoviesapp;

import android.os.Bundle;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.bottom_menu_layout, container, false);

        BottomNavigationView bottomNavigationView = rootView.findViewById(R.id.bottom_navigation_menu);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        final Menu menu = bottomNavigationView.getMenu();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.popular_movies_menu_item:
                        menuItem = menu.getItem(0);
                        break;

                    case R.id.highest_rated_movies_menu_item:
                        menuItem = menu.getItem(1);
                        break;
                }
                menuItem.setChecked(true);

                return false;
                }
            });

        return rootView;
    }

    }
