package com.example.zane.popularmoviesapp;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.zane.popularmoviesapp.Menu.BottomNavigationMenu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = this;

        // Setup Fragments
        MovieListFragment movieListFragment = new MovieListFragment();
        BottomNavigationMenu bottomNavigationMenu = new BottomNavigationMenu();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.movie_frame_layout, movieListFragment)
                .add(R.id.menu_frame_layout, bottomNavigationMenu)
                .commit();
    }
}
