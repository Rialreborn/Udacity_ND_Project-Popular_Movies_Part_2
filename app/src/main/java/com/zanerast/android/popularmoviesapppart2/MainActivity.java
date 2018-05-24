package com.zanerast.android.popularmoviesapppart2;

import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.zanerast.android.popularmoviesapppart2.Menu.BottomNavigationMenu;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.zanerast.android.popularmoviesapppart2.R.layout.activity_main);

        if (savedInstanceState == null) {

            ButterKnife.bind(this);

            // Setup Fragments
            MovieListFragment movieListFragment = new MovieListFragment();
            BottomNavigationMenu bottomNavigationMenu = new BottomNavigationMenu();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(com.zanerast.android.popularmoviesapppart2.R.id.movie_frame_layout, movieListFragment)
                    .add(com.zanerast.android.popularmoviesapppart2.R.id.menu_frame_layout, bottomNavigationMenu)
                    .commit();
        }
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDarker));
    }
}
