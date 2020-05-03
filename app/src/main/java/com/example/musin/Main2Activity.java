package com.example.musin;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * @author Android help Priyam
 * Class at the start which manages the fragments
 * The Main Activity (By default see home fragment
 */

public class Main2Activity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Getting the drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        // Taking the navigation one inside the drawer layout you are using
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();

        // Getting to the content main2
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // Setting the menu button for in the right and also, calling activity_main2 to perform
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        // Set the layout of fragments which have the data where the fragments coresponds and have java file information
        NavigationUI.setupWithNavController(navigationView, navController);

        // Instead of onSupportNavigateUp
        NavigationUI.setupWithNavController(toolbar, navController, mAppBarConfiguration);
    }

    /*
    // For having the click action of the button
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    */
}
