package com.example.zane.popularmoviesapp;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zane.popularmoviesapp.Menu.BottomNavigationMenu;
import com.example.zane.popularmoviesapp.Utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.error_message_tv) TextView error_message_tv;
    @BindView(R.id.linear_layout) LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //noinspection ConstantConditions
        if (Constants.API_KEY == null || Constants.API_KEY.isEmpty()) {
            noApiKeyFound();
        } else if (!networkConnection()) {
            noNetworkFound();
        } else {
            // Setup Fragments
            MovieListFragment movieListFragment = new MovieListFragment();
            BottomNavigationMenu bottomNavigationMenu = new BottomNavigationMenu();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.movie_frame_layout, movieListFragment)
                    .add(R.id.menu_frame_layout, bottomNavigationMenu)
                    .commit();

            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDarker));
        }
    }


    private void noApiKeyFound() {
        linearLayout.setVisibility(View.GONE);
        error_message_tv.setVisibility(View.VISIBLE);
        error_message_tv.setText(getString(R.string.api_key_needed));
    }

    private void noNetworkFound() {
        linearLayout.setVisibility(View.GONE);
        error_message_tv.setVisibility(View.VISIBLE);
        error_message_tv.setText(getString(R.string.no_network));
    }

    private boolean networkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo info = null;
        if (connectivityManager != null) {
            info = connectivityManager.getActiveNetworkInfo();
        }

        return info != null && info.isConnectedOrConnecting();
    }


}
