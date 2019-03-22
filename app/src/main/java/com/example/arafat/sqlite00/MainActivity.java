package com.example.arafat.sqlite00;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    
    private static final String TAG = "MainActivity";
    private TextView textView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);

        try {
            // database create
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
            // create table
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR, age INT(3))");
            //insert data
            myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('arafat', 25)");

            Cursor cursor = myDatabase.rawQuery("SELECT * FROM users", null);
            
            int nameIndex = cursor.getColumnIndex("name");
            int ageIndex = cursor.getColumnIndex("age");

            cursor.moveToFirst();
            while (!cursor.equals(null)) {
                Log.d(TAG, "onCreate: " + cursor.getString(nameIndex));
                Log.i("lol", "onCreate: " +cursor.getString(nameIndex));
                textView.setText(cursor.getString(nameIndex));
                cursor.moveToNext();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
