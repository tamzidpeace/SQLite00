package com.example.arafat.sqlite00;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

class Insert {

    private MainActivity activity;
    private static final String TAG = "Insert";

    Insert(MainActivity activity) {
        this.activity = activity;
    }

    void insertData() {
        SQLiteDatabase mDatabase = activity.myDatabase;
       // Toast.makeText(activity, "Hello, World!", Toast.LENGTH_SHORT).show();
        System.out.println("hello");
        mDatabase.execSQL(" INSERT INTO users(name) VALUES('ARAFAT') ");
        Log.d(TAG, "insertData: called");
    }
}
