package com.example.covid_19vaccination;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Adapter.LocAdapter;
import Model.ListLocation;

public class VaccineLocation extends AppCompatActivity {
    private List<ListLocation> listLoc;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_location);
        setTitle("Locations");
        //Recycler View setup
        recyclerView = findViewById(R.id.recyclerViewLc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listLoc = new ArrayList<>();

        ListLocation loc1 = new ListLocation(getString(R.string.kl));
        ListLocation loc2 = new ListLocation(getString(R.string.labuan));
        ListLocation loc3 = new ListLocation(getString(R.string.putrajaya));
        ListLocation loc4 = new ListLocation(getString(R.string.selangor));
        ListLocation loc5 = new ListLocation(getString(R.string.sarawak));
        ListLocation loc6 = new ListLocation(getString(R.string.sabah));
        ListLocation loc7 = new ListLocation(getString(R.string.johor));
        ListLocation loc8 = new ListLocation(getString(R.string.melaka));
        ListLocation loc9 = new ListLocation(getString(R.string.negeri_sembilan));
        ListLocation loc10 = new ListLocation(getString(R.string.pahang));
        ListLocation loc11 = new ListLocation(getString(R.string.terengganu));
        ListLocation loc12 = new ListLocation(getString(R.string.kelantan));
        ListLocation loc13 = new ListLocation(getString(R.string.perak));
        ListLocation loc14 = new ListLocation(getString(R.string.kedah));
        ListLocation loc15 = new ListLocation(getString(R.string.penang));
        ListLocation loc16 = new ListLocation(getString(R.string.perlis));

        //Insert into List
        listLoc.add(loc1);
        listLoc.add(loc2);
        listLoc.add(loc3);
        listLoc.add(loc4);
        listLoc.add(loc5);
        listLoc.add(loc6);
        listLoc.add(loc7);
        listLoc.add(loc8);
        listLoc.add(loc9);
        listLoc.add(loc10);
        listLoc.add(loc11);
        listLoc.add(loc12);
        listLoc.add(loc13);
        listLoc.add(loc14);
        listLoc.add(loc15);
        listLoc.add(loc16);

        mAdapter = new LocAdapter(this, listLoc);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.faq_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_homeFaq:
                Intent intent = new Intent(this, Dashboard.class);
                startActivity(intent);
                break;

            default:
                // Do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToHome(View view) {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
}