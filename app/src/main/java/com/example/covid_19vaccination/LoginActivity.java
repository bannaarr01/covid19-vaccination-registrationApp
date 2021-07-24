package com.example.covid_19vaccination;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private EditText mEditTextEmail, mEditTextPassword;
    private TextInputLayout mInputTextEmail, mInputTextPassword;
    private String mEmail, mPassword, mName, mVaccineName, mGender,mIc,mPhone,mPhoneType,mState,mAddress,mAppDate,mAppTime;
    private int mAge, role;
    DatabaseManager mDatabase;
    private Button btnRegister, btnLogin;
    SessionManager mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //------------Changing Status Bar and Below NavBar Color---------------------\\
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusColor, this.getTheme()));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusColor));

        } //End of Change Color

        setContentView(R.layout.activity_login);

        //Initialization Session Manager
        mSession = new SessionManager(getApplicationContext());

        //Hooks
        mEditTextEmail = findViewById(R.id.textInputEtEmail);
        mEditTextPassword = findViewById(R.id.textInputEtPassword);
        mInputTextEmail = findViewById(R.id.textInputLayoutEmail);
        mInputTextPassword = findViewById(R.id.textInputLayoutPassword);
        btnRegister = findViewById(R.id.btn_register);
        btnLogin = findViewById(R.id.btn_login);

        //--------------------BackStrokeDynamicColor------------------------------\\
        int color = Color.parseColor("#45A9F8");
        //Color2 from rgb
        //int color2 = Color.rgb(255,0,0);
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_focused}, // focused
                new int[]{android.R.attr.state_hovered}, // hovered
                new int[]{android.R.attr.state_enabled}, // enabled
                new int[]{android.R.attr.state_middle}  //
        };

        int[] colors = new int[]{
                color,
                color,
                color,
                color
        };
        ColorStateList myColorList = new ColorStateList(states, colors);
        mInputTextEmail.setBoxStrokeColorStateList(myColorList);
        mInputTextPassword.setBoxStrokeColorStateList(myColorList);
//--------------------EndOfBackStrokeDynamicColor------------------------------\\

        //Capture onTextEvents
        mEditTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateEmail(); //Validate email while typing
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mEditTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePassword(); //Validate password while typing
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateEmail() | !validatePassword())
                    return;

                loginSuccessful();
            }
        });

    }//end of onCreate

    private boolean validateEmail() {
        mEmail = mEditTextEmail.getText().toString().trim();
        String checkEmail = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$";
        if (mEmail.isEmpty()) {
            mInputTextEmail.setErrorEnabled(true);
            mInputTextEmail.setError("*Required");
            return false;
        } else if (!mEmail.matches(checkEmail)) { //To check if input is in the correct email format
            mInputTextEmail.setErrorEnabled(true);
            mInputTextEmail.setError("*Please enter a valid email");
            return false;
        } else {
            mInputTextEmail.setError(null);
            mInputTextEmail.setHelperTextEnabled(false); //Remove *Required
            return true;
        }//End of email Validate
    }

    private boolean validatePassword() {
        mPassword = mEditTextPassword.getText().toString();
        if (mPassword.isEmpty()) {
            mInputTextPassword.setErrorEnabled(true);
            mInputTextPassword.setError("*Required");
            return false;
        } else if (mPassword.length() < 4) { //To check if input is in the correct email format
            mInputTextPassword.setErrorEnabled(true);
            mInputTextPassword.setError("*Please enter a valid password");
            return false;
        } else {
            mInputTextPassword.setError(null);
            mInputTextPassword.setHelperTextEnabled(false); //Remove *Required
            return true;
        }//End of password Validate

    }

    private void loginSuccessful(){
        mDatabase = new DatabaseManager(this);
        if(mDatabase.checkLogin(mEmail,mPassword)) {
            Cursor details = mDatabase.getOneUserDetails(mEmail);
            details.moveToFirst();
            mVaccineName = details.getString(1);
            mName = details.getString(4);
            mAge = details.getInt(5);
            mGender = details.getString(6);
            mIc = details.getString(7);
            mPhone = details.getString(8);
            mPhoneType = details.getString(9);
            mState = details.getString(10);
            mAddress = details.getString(11);
            mAppDate = details.getString(12);
            mAppTime= details.getString(13);

            //Creating Session for the Logged in User
            mSession.createLoginSession(mEmail, mName);
            //Cursor userRole = mDatabase.chekAdmin(mEmail);
            // userRole.moveToFirst();
            int role = details.getInt(14);
            if (role==1){
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(intent);
            } else if (role==0) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                //Passing the User Values to User Activity
                Bundle b = new Bundle();
                b.putString("VaccineName", mVaccineName);
                b.putInt("Age", mAge);
                b.putString("Gender", mGender);
                b.putString("IC", mIc);
                b.putString("Phone", mPhone);
                b.putString("PhoneType", mPhoneType);
                b.putString("State", mState);
                b.putString("Address", mAddress);
                b.putString("AppDate", mAppDate);
                b.putString("AppTime", mAppTime);

                intent.putExtras(b);
                startActivity(intent);
            }
        }
        else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            mEditTextPassword.setText("");
            mEditTextEmail.setText("");
        }
    }

}