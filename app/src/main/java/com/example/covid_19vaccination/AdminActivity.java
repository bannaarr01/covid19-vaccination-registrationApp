package com.example.covid_19vaccination;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import Adapter.AdminAdapter;
import Model.User;

public class AdminActivity extends AppCompatActivity
        implements OnQueryTextListener, AdapterView.OnItemSelectedListener {
    private ArrayList<User> userList;
    private RecyclerView recyclerView;
    private AdminAdapter adapter;
    private DatabaseManager mDatabase;
    private SessionManager mSession;
    private String currentSearchText = "";
    private SearchView searchView;
    private Spinner spinner;

    public void onBackPressed() {
        if (mSession.isLoggedIn()) {
            Toast.makeText(this, "You are logged in ", Toast.LENGTH_SHORT).show();
            mSession.isLoggedIn();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //Spinner set up for filtering states
        spinner = findViewById(R.id.spinnerFilter);
        if (spinner != null)
            spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filterStates, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (spinner != null)
            spinner.setAdapter(adapter);

        mDatabase = new DatabaseManager(this);
        userList = new ArrayList<>();

        recyclerView = findViewById(R.id.rvUser);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setTitle("Admin");
        mSession = new SessionManager(getApplicationContext());

        loadUsersFromDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);

        MenuItem search = menu.findItem(R.id.search);
        searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_Logout:
                mSession.logoutSession();
                finish();
                break;

            default:
                // Do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        currentSearchText = newText;
        filterList(spinner.getSelectedItem().toString());
        return true;
    }

    private void filterList(String status) {
        ArrayList<User> filteredList = new ArrayList<>();


        for (User user : userList) {
            String name = user.getName().toLowerCase();
            String email = user.getEmail().toLowerCase();
            String icPassport = user.getIcPassport().toLowerCase();

            if (user.getState().contains(status))
                if (currentSearchText == "")
                    filteredList.add(user);
                else if (name.contains(currentSearchText))
                    filteredList.add(user);
                else if (email.contains(currentSearchText))
                    filteredList.add(user);
                else if (icPassport.contains(currentSearchText))
                    filteredList.add(user);
        }
        adapter.setFilter(filteredList);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
        if (adapterView.getSelectedItemPosition() == 0)
            adapter.loadUsersFromDatabaseAgain();
        else
            filterList(adapterView.getItemAtPosition(i).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void loadUsersFromDatabase() {
        Cursor cursor = mDatabase.getAllUsers();

        if (cursor.moveToFirst()) {
            do {
                userList.add(new User(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getString(9),
                        cursor.getString(10),
                        cursor.getString(11),
                        cursor.getString(12),
                        cursor.getString(13),
                        cursor.getInt(14)
                ));
            } while (cursor.moveToNext());
            //Passing the database manager instance this tim to the adapter
            adapter = new AdminAdapter(this, R.layout.list_layout_user, userList, mDatabase);
            recyclerView.setAdapter(adapter);
        }
    }
}