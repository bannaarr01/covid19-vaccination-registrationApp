package com.example.covid_19vaccination;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import Model.User;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "VaccinationDb";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_VACCINENAME = "vaccineName";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_IC = "ic";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_PHONETYPE = "phonetype";
    private static final String COLUMN_STATE = "state";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_APPDATE = "appDate";
    private static final String COLUMN_APPTIME = "appTime";
    private static final String COLUMN_ROLE= "role";
    User user;

    public DatabaseManager(@Nullable Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating user table
        String sql = " CREATE TABLE " + TABLE_NAME + "(\n" +
                "  " +"id INTEGER NOT NULL CONSTRAINT users_pk PRIMARY KEY AUTOINCREMENT , \n"+
                "  " + COLUMN_VACCINENAME + " varchar(200) NOT NULL, \n " +
                "  " + COLUMN_EMAIL + " varchar(200) NOT NULL, \n " +
                "  " + COLUMN_PASSWORD + " varchar(200) NOT NULL, \n " +
                "  " + COLUMN_NAME + " varchar(200) NOT NULL, \n " +
                "  " + COLUMN_AGE + " int(2) NOT NULL, \n " +
                "  " + COLUMN_GENDER + " varchar(40) NOT NULL, \n " +
                "  " + COLUMN_IC + " varchar(50) NOT NULL, \n " +
                "  " + COLUMN_PHONE + " varchar(11) NOT NULL, \n " +
                "  " + COLUMN_PHONETYPE + " varchar(20) NOT NULL, \n " +
                "  " + COLUMN_STATE + " varchar(100) NOT NULL, \n " +
                "  " + COLUMN_ADDRESS + " varchar(255) NOT NULL, \n " +
                "  " + COLUMN_APPDATE + " date NOT NULL, \n " +
                "  " + COLUMN_APPTIME + " varchar(10) NOT NULL, \n " +
                "  " + COLUMN_ROLE + " tinyint NOT NULL\n " +
                ");";
        // Executing the string to create the table
        db.execSQL(sql);
    }//creating faq table

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = " DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }

    boolean addUser(String vaccineName, String email, String password, String name, int age, String gender,
                        String ic, String phone, String phoneType, String state, String address, String appDate, String appTime,
                        int role){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_VACCINENAME, vaccineName);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_AGE, age);
        contentValues.put(COLUMN_GENDER, gender);
        contentValues.put(COLUMN_IC, ic);
        contentValues.put(COLUMN_PHONE, phone);
        contentValues.put(COLUMN_PHONETYPE, phoneType);
        contentValues.put(COLUMN_STATE, state);
        contentValues.put(COLUMN_ADDRESS, address);
        contentValues.put(COLUMN_APPDATE, appDate);
        contentValues.put(COLUMN_APPTIME, appTime);
        contentValues.put(COLUMN_ROLE, role);
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_NAME, null, contentValues) != -1;
    }

    public Cursor getAllUsers() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ROLE + "=" + 0, null);
    }

    public boolean updateUser(int id, String phone, String phoneType, String state, String address, String appDate, String appTime){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PHONE, phone);
        contentValues.put(COLUMN_PHONETYPE, phoneType);
        contentValues.put(COLUMN_STATE, state);
        contentValues.put(COLUMN_ADDRESS, address);
        contentValues.put(COLUMN_APPDATE, appDate);
        contentValues.put(COLUMN_APPTIME, appTime);
        return db.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }

    public boolean deleteUser(int id){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID +"=?", new String[]{String.valueOf(id)}) == 1;
    }

    public boolean checkLogin(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users where email=? AND password=?",
                new String[] {email, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Cursor getOneUserDetails(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM users where email=?",
                new String[] {email});
        return result;

    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users where email=?",
                new String[] {email});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean checkIc(String ic) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users where ic=?",
                new String[] {ic});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
}
