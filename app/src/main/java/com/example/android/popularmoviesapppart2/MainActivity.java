package com.example.android.popularmoviesapppart2;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.popularmoviesapppart2.Menu.BottomNavigationMenu;
import com.example.android.popularmoviesapppart2.Utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.android.popularmoviesapppart2.R.layout.activity_main);

        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.MOVIE_SORT_ORDER_KEY, Constants.MOVIE_SORT_ORDER_POPULAR).apply();

            // Setup Fragments
            MovieListFragment movieListFragment = new MovieListFragment();
            BottomNavigationMenu bottomNavigationMenu = new BottomNavigationMenu();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(com.example.android.popularmoviesapppart2.R.id.movie_frame_layout, movieListFragment)
                    .add(com.example.android.popularmoviesapppart2.R.id.menu_frame_layout, bottomNavigationMenu)
                    .commit();

            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDarker));

    }
}
