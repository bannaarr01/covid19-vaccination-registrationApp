package com.example.covid_19vaccination;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class UserActivity extends AppCompatActivity {
    SessionManager mSession;
    Button btnLogout, btnLocation;
    TextView mTextView, mTextViewLogo;
    private TextView movingText;
    private String mEmail, mPassword, mVaccineName, mGender, mIc, mPhone, mPhoneType, mState, mAddress, mAppDate, mAppTime;
    private int mAge;

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
        this.setTitle("Account");
        setContentView(R.layout.activity_user);
        //---Moving Text--//
        movingText = findViewById(R.id.tvMovingText);
        movingText.setSelected(true);

        //Init Session Manager
        mSession = new SessionManager(getApplicationContext());
       // mSession.checkLoginSession();//check Login

        //Getting User from Session
        HashMap<String, String> userDetails = mSession.getUserDetails();
        String email = userDetails.get(mSession.KEY_EMAIL);
        String name = userDetails.get(mSession.KEY_NAME);

        //Creating Profile Logo with Name Initials
        String nameLogo = name;
        mTextViewLogo = findViewById(R.id.tvprofileLogo);
        mTextViewLogo.setText(nameLogo.replaceAll("^\\s*([a-zA-Z]).*\\s+([a-zA-Z])\\S+$", "$1$2").toUpperCase());


        //Set Email and Name using session
        mTextView = findViewById(R.id.tvEmailText);
        mTextView.setText(email);

        mTextView = findViewById(R.id.tvNameText);
        mTextView.setText("Welcome " + name);

        //Toast.makeText(this,"Session status: " + mSession.isLoggedIn(), Toast.LENGTH_SHORT).show();

        //Get Values and Set to TextView
        Bundle b = getIntent().getExtras();
        mTextView = findViewById(R.id.tvVaccineNameText);
        mVaccineName = b.getString("VaccineName");
        mTextView.setText(mVaccineName);

        mTextView = findViewById(R.id.tvAgeText);
        mAge = b.getInt("Age");
        String age;
        age = String.valueOf(mAge);
        mTextView.setText(age);

        mTextView = findViewById(R.id.tvGenderText);
        mGender = b.getString("Gender");
        mTextView.setText(mGender);

        mTextView = findViewById(R.id.tvIcPassportText);
        mIc = b.getString("IC");
        mTextView.setText(mIc);

        mTextView = findViewById(R.id.tvPhoneText);
        mPhone = b.getString("Phone");
        mPhoneType = b.getString("PhoneType");
        mTextView.setText(mPhone + " (" + mPhoneType + ")");

        mTextView = findViewById(R.id.tvStateText);
        mState = b.getString("State");
        mTextView.setText(mState);

        mTextView = findViewById(R.id.tvAddressText);
        mAddress = b.getString("Address");
        mTextView.setText(mAddress);

        mTextView = findViewById(R.id.tvAppointDate);
        mAppDate = b.getString("AppDate");
        mTextView.setText(mAppDate);

        mTextView = findViewById(R.id.tvAppointTime);
        mAppTime = b.getString("AppTime");
        mTextView.setText(mAppTime);

        btnLogout = findViewById(R.id.btnUserLogout);
        btnLocation = findViewById(R.id.btnCheckVaccineCenter);

        //Button LogoutListener
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSession.logoutSession();
                finish();
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSession.logoutSession();
                Intent intent = new Intent(getApplicationContext(), VaccineLocation.class);
                startActivity(intent);
                finish();
            }
        });

    }
}