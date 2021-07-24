package com.example.covid_19vaccination;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ConfirmationActivity extends AppCompatActivity {
    private TextView mTextView;
    private String mName, vName, mGender, mIcPassport, mPhone, mPhoneType, mState, mAddress, mEmail, mDate, mTime;
    private int mAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.reg_success);
        setContentView(R.layout.activity_confirmation);

        //To get the phone focus to the top of the activity
        mTextView = findViewById(R.id.heading2);
        mTextView.setFocusableInTouchMode(true);

        //Getting values from previous activity using intent and setting up the values to the TextViews
        Intent intent = getIntent();

        mTextView = findViewById(R.id.tvVaccineNameText);
        vName = intent.getStringExtra("Vaccine");
        mTextView.setText(vName);

        mTextView = findViewById(R.id.tvNameText);
        mName = intent.getStringExtra("Name");
        mTextView.setText(mName);

        mTextView = findViewById(R.id.tvAgeText);
        mAge = intent.getIntExtra("Age", 0);
        mTextView.setText("" + mAge);

        mTextView = findViewById(R.id.tvGenderText);
        mGender = intent.getStringExtra("Gender");
        mTextView.setText(mGender);

        mTextView = findViewById(R.id.tvIcPassportText);
        mIcPassport = intent.getStringExtra("IcPassport");
        mTextView.setText(mIcPassport);

        mTextView = findViewById(R.id.tvPhoneText);
        mPhone = intent.getStringExtra("Phone");
        mPhoneType = intent.getStringExtra("PhoneType");
        mTextView.setText(mPhone + " (" + mPhoneType + ")");

        mTextView = findViewById(R.id.tvStateText);
        mState = intent.getStringExtra("State");
        mTextView.setText(mState);

        mTextView = findViewById(R.id.tvAddressText);
        mAddress = intent.getStringExtra("Address");
        mTextView.setText(mAddress);

        mTextView = findViewById(R.id.tvEmailText);
        mEmail = intent.getStringExtra("Email");
        mTextView.setText(mEmail);

        mTextView = findViewById(R.id.tvAppointDate);
        mDate = intent.getStringExtra("Date");
        mTextView.setText(mDate);

        mTextView = findViewById(R.id.tvAppointTime);
        mTime = intent.getStringExtra("Time");
        mTextView.setText(mTime);
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

    public void goToLocation(View view) {
        Intent intent = new Intent(this, VaccineLocation.class);
        startActivity(intent);
    }
}