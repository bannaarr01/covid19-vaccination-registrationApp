package Adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19vaccination.DatabaseManager;
import com.example.covid_19vaccination.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Model.User;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {
    Context mCtx;
    int layoutRes;
    ArrayList<User> userList;
    DatabaseManager mDatabase;
    EditText mEditText;
    DatePickerDialog mPicker;
    String sDay, sMonth, sYear;

    public AdminAdapter(Context mCtx, int layoutRes, ArrayList<User> userList, DatabaseManager mDatabase) {
        this.mCtx = mCtx;
        this.userList = userList;
        this.mDatabase = mDatabase;
        this.layoutRes = layoutRes;
    }

    @NonNull
    @Override
    public AdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.textViewName.setText("Name: " + user.getName());
        holder.textViewVaccine.setText("Vaccination: " + user.getVaccineName());
        holder.textViewAge.setText("Age: " + user.getAge());
        holder.textViewGender.setText("Gender: " + user.getGender());
        holder.textViewIcPassport.setText("IC / Passport No: " + user.getIcPassport());
        holder.textViewPhone.setText("Phone: " + user.getPhone() + "(" + user.getPhoneType() + ")");
        holder.textViewState.setText("State: " + user.getState());
        holder.textViewAddress.setText("Address: " + user.getAddress());
        holder.textViewEmail.setText("Email: " + user.getEmail());
        holder.textViewDate.setText("Appointment Date and Time: " + user.getAppDate() + " at " + user.getAppTime());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser(user);
            }
        });

        holder.btnUpdate.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAUser(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setFilter(ArrayList<User> newList) {
        userList = new ArrayList<>();
        userList.addAll(newList);
        notifyDataSetChanged();
    }

    private void updateAUser(final User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.dialog_update_user, null);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //from dialogue_update_user.xml
        final EditText editTextPhone = view.findViewById(R.id.etPhone2);
        final Spinner spinnerPhoneType = view.findViewById(R.id.spinnerPhone2);
        final Spinner spinnerState = view.findViewById(R.id.spinnerState2);
        final EditText editTextAddress = view.findViewById(R.id.etAddress2);
        final EditText editTextDate = view.findViewById(R.id.etDate);
        final Spinner spinnerTime = view.findViewById(R.id.spinnerTime);

        editTextPhone.setText(user.getPhone());
        editTextAddress.setText(user.getAddress());
        editTextDate.setText(user.getAppDate());

        switch (user.getPhoneType()) {
            case "Mobile":
                spinnerPhoneType.setSelection(0);
                break;
            case "Home":
                spinnerPhoneType.setSelection(1);
                break;
            case "Work":
                spinnerPhoneType.setSelection(2);
                break;
            case "Other":
                spinnerPhoneType.setSelection(3);
                break;
            default:
                break;
        }

        switch (user.getState()) {
            case "Select your state":
                spinnerState.setSelection(0);
                break;
            case "Johor":
                spinnerState.setSelection(1);
                break;
            case "Kedah":
                spinnerState.setSelection(2);
                break;
            case "Kelantan":
                spinnerState.setSelection(3);
                break;
            case "Kuala Lumpur":
                spinnerState.setSelection(4);
                break;
            case "Labuan":
                spinnerState.setSelection(5);
                break;
            case "Melaka":
                spinnerState.setSelection(6);
                break;
            case "Negeri Sembilan":
                spinnerState.setSelection(7);
                break;
            case "Pahang":
                spinnerState.setSelection(8);
                break;
            case "Penang":
                spinnerState.setSelection(9);
                break;
            case "Perak":
                spinnerState.setSelection(10);
                break;
            case "Perlis":
                spinnerState.setSelection(11);
                break;
            case "Putrajaya":
                spinnerState.setSelection(12);
                break;
            case "Sabah":
                spinnerState.setSelection(13);
                break;
            case "Sarawak":
                spinnerState.setSelection(14);
                break;
            case "Terangganu":
                spinnerState.setSelection(15);
                break;
            case "Selangor":
                spinnerState.setSelection(16);
                break;
            default:
                break;
        }

        switch (user.getAppTime()) {
            case "10:00":
                spinnerTime.setSelection(0);
                break;
            case "12:00":
                spinnerTime.setSelection(1);
                break;
            case "14:00":
                spinnerTime.setSelection(2);
                break;
            case "16:00":
                spinnerTime.setSelection(3);
                break;
            default:
                break;
        }

        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                mEditText = view.findViewById(R.id.tvInvisibleError2); //To remove the error message in spinnerState
                mEditText.setError(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        sDay = editTextDate.getText().toString().substring(0, 2);
        sMonth = editTextDate.getText().toString().substring(3, 5);
        sYear = editTextDate.getText().toString().substring(6);
        //Include Updating the date on the dialog layout
        view.findViewById(R.id.btnDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = Integer.parseInt(sDay);
                int month = Integer.parseInt(sMonth) - 1;
                int year = Integer.parseInt(sYear);

                mPicker = new DatePickerDialog(mCtx, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dat = dayOfMonth + "-" + (month + 1) + "-" + year;
                        if (dat.length() == 9)
                            dat = dayOfMonth + "-0" + (month + 1) + "-" + year;

                        editTextDate.setText(dat);
                        sDay = editTextDate.getText().toString().substring(0, 2);
                        sMonth = editTextDate.getText().toString().substring(3, 5);
                        sYear = editTextDate.getText().toString().substring(6);
                    }
                }, year, month, day);
                mPicker.show();
            }
        });

        view.findViewById(R.id.btAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = editTextPhone.getText().toString();
                String phoneType = spinnerPhoneType.getSelectedItem().toString();
                String state = spinnerState.getSelectedItem().toString();
                String address = editTextAddress.getText().toString();
                String appDate = editTextDate.getText().toString();
                String appTime = spinnerTime.getSelectedItem().toString();

                if (phone.length() < 10) { //Phone number length must be at least 7 numbers
                    editTextPhone.setError("Enter a valid phone number");
                    return;
                }

                if (address.isEmpty()) {
                    editTextAddress.setError("Please enter address");
                    return;
                }

                if (spinnerState.getSelectedItemPosition() == 0) {
                    mEditText = view.findViewById(R.id.tvInvisibleError2);
                    mEditText.setError("Please select state");
                    return;
                }

                if (appDate.isEmpty()) {
                    editTextDate.setError("Please select a date");
                    return;
                } else { //date validation, must not be less than tomorrow
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String date = sdf.format(cal.getTime());

                    try {
                        if (sdf.parse(appDate).before(sdf.parse(date))) {
                            editTextDate.setError("Appointment Date must be at least from tomorrow");//If start date is before end date
                            return;
                        } else if (sdf.parse(appDate).equals(sdf.parse(date))) {
                            editTextDate.setError("Appointment Date must be at least from tomorrow");//If two dates are equal
                            return;
                        }
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                // calling the update method from database manager instance
                if (mDatabase.updateUser(user.getId(), phone, phoneType, state, address, appDate, appTime)) {
                    Toast.makeText(mCtx, "User Updated", Toast.LENGTH_SHORT).show();
                    loadUsersFromDatabaseAgain();
                }
                alertDialog.dismiss();
            }
        });
    }//update user

    private void deleteUser(final User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setTitle("Are you sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Calling the delete method from the database manager instance
                if (mDatabase.deleteUser(user.getId()))
                    loadUsersFromDatabaseAgain();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }//deleteUser

    public void loadUsersFromDatabaseAgain() {
        //calling the read method from database instance
        Cursor cursor = mDatabase.getAllUsers();

        userList.clear();
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
        }
        notifyDataSetChanged();
    } //load users

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName, textViewVaccine, textViewAge, textViewGender, textViewIcPassport,
                textViewPhone, textViewState, textViewAddress, textViewEmail, textViewDate;
        public Button btnDelete, btnUpdate;

        public ViewHolder(View itemView) {
            super(itemView);

            //from list_layout_user
            textViewName = itemView.findViewById(R.id.tvName2);
            textViewVaccine = itemView.findViewById(R.id.tvVaccination);
            textViewAge = itemView.findViewById(R.id.tvAge2);
            textViewGender = itemView.findViewById(R.id.tvGender2);
            textViewIcPassport = itemView.findViewById(R.id.tvIcPassport2);
            textViewPhone = itemView.findViewById(R.id.tvPhone2);
            textViewState = itemView.findViewById(R.id.tvState2);
            textViewAddress = itemView.findViewById(R.id.tvAddress2);
            textViewEmail = itemView.findViewById(R.id.tvEmail2);
            textViewDate = itemView.findViewById(R.id.tvDate);

            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
        }
    }
}
