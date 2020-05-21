package com.example.staysafeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loadFragment(new FragmentHome());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        TextView txtHome    = (TextView) bottomNavigationView.findViewById(R.id.menuHome).findViewById(R.id.largeLabel);
        TextView txtStats   = (TextView) bottomNavigationView.findViewById(R.id.menuStats).findViewById(R.id.largeLabel);
        TextView txtNews    = (TextView) bottomNavigationView.findViewById(R.id.menuNews).findViewById(R.id.largeLabel);
        TextView txtCampaign= (TextView) bottomNavigationView.findViewById(R.id.menuCampaign).findViewById(R.id.largeLabel);
        TextView txtHotline = (TextView) bottomNavigationView.findViewById(R.id.menuHotline).findViewById(R.id.largeLabel);
        txtHome.setTextSize(11);
        txtStats.setTextSize(11);
        txtNews.setTextSize(11);
        txtCampaign.setTextSize(11);
        txtHotline.setTextSize(11);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.home_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.menuHome:
                fragment = new FragmentHome();
                break;
            case R.id.menuStats:
                fragment = new FragmentStats();
                break;
            case R.id.menuNews:
                fragment = new FragmentNews();
                break;
            case R.id.menuCampaign:
                fragment = new FragmentCampaign();
                break;
            case R.id.menuHotline:
                fragment = new FragmentHotline();
                break;
        }
        return loadFragment(fragment);
    }
}