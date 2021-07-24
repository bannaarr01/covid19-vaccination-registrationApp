package com.example.covid_19vaccination;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    private String[] mTime = {"10:00", "12:00", "14:00", "16:00"};
    private EditText mEditText;
    private RadioButton mRadioButton;
    private RadioGroup mRadioGroup;
    private String mName, vName, mGender, mIcPassport, mIcPassportC, mPhone, mPhoneC, mPhoneType,
            mState, mAddress, mEmail, mPassword, mPasswordC, date, time;
    private int mAge;
    DatabaseManager mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        vName = intent.getStringExtra("Name");
        setTitle(vName + " " + getString(R.string.vaccine_reg)); //setting up title of the activity
        setContentView(R.layout.activity_register);

        //setting up the 2 spinners process
        //spinner for phone types
        Spinner spinner = findViewById(R.id.spinnerPhone);
        if (spinner != null)
            spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.phoneTypes, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        if (spinner != null)
            spinner.setAdapter(adapter);

        //spinner for states
        spinner = findViewById(R.id.spinnerState);
        if (spinner != null)
            spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.states, android.R.layout.simple_spinner_item);

        adapter2.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        if (spinner != null)
            spinner.setAdapter(adapter2);
    }//End of onCreate

    //When submit button is clicked
    public void submitForm(View view) {
        //Validation process before passing the variables
        if (!validateName() | !validateAge() | !validateGender() | !validateIcPassport() | !validateIcPassportConfirm()
                | !validatePhoneNumber() | !validatePhoneNumberConfirm() | !validateState() | !validateAddress()
                | !validateEmail() | !validatePasword() | !validatePaswordConfirm()) {
            return;
        }

        mDatabase = new DatabaseManager(this);
        //Checking if the email or IC/Passport already exists in the system
        if (mDatabase.checkEmail(mEmail)){
            Toast.makeText(this, getString(R.string.emailUsed), Toast.LENGTH_SHORT).show();
            return;
        }

        if (mDatabase.checkIc(mIcPassport)){
            Toast.makeText(this, getString(R.string.icUsed), Toast.LENGTH_SHORT).show();
            return;
        }

        //Assigning appointment for first dose
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        date = sdf.format(cal.getTime());

        Random rand = new Random();
        time = mTime[rand.nextInt(4)];

        mDatabase.addUser(vName, mEmail, mPassword, mName, mAge, mGender, mIcPassport, mPhone, mPhoneType, mState, mAddress, date, time, 0);

        Toast.makeText(this, getString(R.string.successful), Toast.LENGTH_SHORT).show();
        //Passing variables to the next activity (ConfirmationActivity) using external intent
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra("Name", mName);
        intent.putExtra("Age", mAge);
        intent.putExtra("Gender", mGender);
        intent.putExtra("IcPassport", mIcPassport);
        intent.putExtra("Phone", mPhone);
        intent.putExtra("PhoneType", mPhoneType);
        intent.putExtra("State", mState);
        intent.putExtra("Address", mAddress);
        intent.putExtra("Email", mEmail);
        intent.putExtra("Password", mPassword);
        intent.putExtra("Vaccine", vName);
        intent.putExtra("Date", date);
        intent.putExtra("Time", time);
        startActivity(intent);
        finish(); //Remove the activity from working activity when done
    }

    //When Radio Button is clicked
    public void onRadioButtonClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        mRadioButton = findViewById(R.id.rbFemale);

        //Checking which radio button was clicked.
        switch (view.getId()) {
            case R.id.rbMale:
                if (checked) {
                    mGender = getString(R.string.rbMaleText);
                    mRadioButton.setError(null);
                }
                break;
            case R.id.rbFemale:
                if (checked) {
                    mGender = getString(R.string.rbFemaleText);
                    mRadioButton.setError(null);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
        //Using if-else to identify which spinner was interacted to assign the values correctly
        if (adapterView.getId() == R.id.spinnerPhone)
            mPhoneType = adapterView.getItemAtPosition(i).toString();
        else if (adapterView.getId() == R.id.spinnerState) {
            mState = adapterView.getItemAtPosition(i).toString();
            mEditText = findViewById(R.id.tvInvisibleError); //To remove the error message in spinnerState
            mEditText.setError(null);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Validation functions
    private boolean validateName() {
        mEditText = findViewById(R.id.etName);
        mName = mEditText.getText().toString().trim();

        if (mName.isEmpty()) {
            mEditText.setError("Please enter your name");
            return false;
        } else {
            mEditText.setError(null);
            return true;
        }
    }


    private boolean validateAge() {
        mEditText = findViewById(R.id.etAge);

        if (mEditText.getText().toString().trim().isEmpty()) {
            mEditText.setError("Please enter your age");
            return false;
        }

        mAge = Integer.parseInt(mEditText.getText().toString().trim());

        //Age must be greater than 18 to apply
        if (mAge < 18) {
            mEditText.setError("Your age must be 18 or older to apply");
            return false;
        } else {
            mRadioButton.setError(null);
            return true;
        }
    }

    private boolean validateGender() {
        mRadioButton = findViewById(R.id.rbFemale);
        mRadioGroup = findViewById(R.id.radioGroup);

        //Setting up error message on the radio button
        if (mRadioGroup.getCheckedRadioButtonId() == -1) {
            mRadioButton.setError("Please Select Gender");
            mRadioButton.setFocusable(true);
            mRadioButton.setFocusableInTouchMode(true);
            return false;
        } else {
            mRadioButton.setError(null);
            return true;
        }
    }

    private boolean validateIcPassport() {
        mEditText = findViewById(R.id.etIcPassport);
        mIcPassport = mEditText.getText().toString().trim();

        if (mIcPassport.isEmpty()) {
            mEditText.setError("Please enter your IC / Passport No.");
            return false;
        } else {
            mEditText.setError(null);
            return true;
        }
    }


    private boolean validateIcPassportConfirm() {
        mEditText = findViewById(R.id.etIcPassportConfirm);
        mIcPassportC = mEditText.getText().toString().trim();

        if (mIcPassportC.isEmpty()) {
            mEditText.setError("Please confirm your IC / Passport No.");
            return false;
        } else if (!mIcPassportC.toLowerCase().equals(mIcPassport.toLowerCase())) { //.toLowerCase() to make the comparison not case-sensitive
            mEditText.setError("Your IC / Passport No. must match");
            return false;
        } else {
            mEditText.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNumber() {
        mEditText = findViewById(R.id.etPhone);
        mPhone = mEditText.getText().toString();

        if (mPhone.length() < 10) { //Phone number length must be at least 7 numbers
            mEditText.setError("Enter a valid phone number");
            return false;
        } else {
            mEditText.setError(null);
            return true;
        }
    }


    private boolean validatePhoneNumberConfirm() {
        mEditText = findViewById(R.id.etPhoneConfirm);
        mPhoneC = mEditText.getText().toString();

        if (mPhoneC.isEmpty()) {
            mEditText.setError("Please confirm your phone number");
            return false;
        } else if (!mPhoneC.equals(mPhone)) {
            mEditText.setError("Your phone number must match");
            return false;
        } else {
            mEditText.setError(null);
            return true;
        }
    }


    private boolean validateState() {
        String[] states = getResources().getStringArray(R.array.states);
        if (mState.equals(states[0])) {
            mEditText = findViewById(R.id.tvInvisibleError);
            mEditText.setError("Please select your state");
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAddress() {
        mEditText = findViewById(R.id.etAddress);
        mAddress = mEditText.getText().toString().trim();

        if (mAddress.isEmpty()) {
            mEditText.setError("Please enter your address");
            return false;
        } else {
            mEditText.setError(null);
            return true;
        }
    }


    private boolean validateEmail() {
        mEditText = findViewById(R.id.etEmail);
        mEmail = mEditText.getText().toString().trim();
        String checkEmail = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$";

        if (mEmail.isEmpty()) {
            mEditText.setError("Field can not be empty");
            return false;
        } else if (!mEmail.matches(checkEmail)) { //To check if input is in the correct email format
            mEditText.setError("Invalid email entered");
            return false;
        } else {
            mEditText.setError(null);
            return true;
        }
    }

    private boolean validatePasword() {
        mEditText = findViewById(R.id.etPassword);
        mPassword = mEditText.getText().toString().trim();

        if (mPassword.length() <4) {
            mEditText.setError("Your password must be at least 4 characters");
            return false;
        } else {
            mEditText.setError(null);
            return true;
        }
    }

    private boolean validatePaswordConfirm() {
        mEditText = findViewById(R.id.etPasswordConfirm);
        mPasswordC = mEditText.getText().toString();

        if (mPasswordC.isEmpty()) {
            mEditText.setError("Please confirm password");
            return false;
        } else if (!mPasswordC.equals(mPassword)) {
            mEditText.setError("Your passwords must match");
            return false;
        } else {
            mEditText.setError(null);
            return true;
        }
    }
}//End of Class