package com.example.smarthouse01;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.smarthouse01.fragment.Account;
import com.example.smarthouse01.fragment.Home;
import com.example.smarthouse01.fragment.Logout;
import com.example.smarthouse01.fragment.Profile;
import com.google.android.material.navigation.NavigationView;

public class Menu_nav extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int fragment_home = 0;
    private static final int fragment_account = 1;
    private static final int fragment_logout = 2;
    private static final int fragment_profile = 3;

    private int currentFragment = fragment_home;

    DrawerLayout drawerLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_nav);

        Toolbar toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar
                , R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new Home());
        navigationView.getMenu().findItem(R.id.menu_home).setChecked(true);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_home){
            if (currentFragment != fragment_home){
                replaceFragment(new Home());
                currentFragment = fragment_home;
            }

        } else if (id==R.id.menu_account){
            if (currentFragment != fragment_account){
                replaceFragment(new Account());
                currentFragment = fragment_account;
            }

        } else if (id==R.id.menu_logout){
            if (currentFragment != fragment_logout){
                replaceFragment(new Logout());
                currentFragment = fragment_logout;
            }
        } else if (id==R.id.menu_profile){
            if (currentFragment != fragment_profile){
                replaceFragment(new Profile());
                currentFragment = fragment_profile;
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void replaceFragment (Fragment frag){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, frag);
        transaction.commit();
    }
}
