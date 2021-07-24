package com.example.covid_19vaccination;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import Adapter.MyAdapter;
import Model.ListItem;


public class Dashboard extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItem;
    int images[] = {R.drawable.astrazaneca, R.drawable.sinovac, R.drawable.pfizer};


    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    Toolbar mToolbar;
    //SessionManager mSession;

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    } //To avoid closing the app on back Pressed Button once the drawer display


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusColor, this.getTheme()));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusColor));

        }

        setContentView(R.layout.activity_dashboard);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mToolbar = findViewById(R.id.toolbar);

        //use ds tool bar as action bar
        setSupportActionBar(mToolbar);

        //Navigation Drawer Menu -- Handle the open and close and selection
        mNavigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Select Home by Default
        mNavigationView.setCheckedItem(R.id.nav_home);

        //This Handle the click on the Navigation Items
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_home: //do Nothing cos we are already in Home <--
                                break;
                            case R.id.nav_login:
                                Intent intent = new Intent(Dashboard.this, LoginActivity.class);
                                startActivity(intent);
                                break;

                            case R.id.nav_faq:
                                Intent intentFaq = new Intent(Dashboard.this, FaqActivity.class);
                                startActivity(intentFaq);
                                break;

                        }

                        //when one menu is selected, it close d drawer and perform d action
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });

        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItem = new ArrayList<>();
        ListItem item1 = new ListItem("AstraZeneca", getString(R.string.astrazeneca));
        ListItem item2 = new ListItem("Sinovac-Coronavac", getString(R.string.sinovac));
        ListItem item3 = new ListItem("Pfizer-BioNTech", getString(R.string.pfizer));

        //Insert into List
        listItem.add(item1);
        listItem.add(item2);
        listItem.add(item3);

        adapter = new MyAdapter(this, listItem, images);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_login:
                Intent intent = new Intent(Dashboard.this, LoginActivity.class);
                startActivity(intent);
                return true;

            default:
                // Do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    public void callNow(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:+601111693190"));
        startActivity(intent);
    }
}