package com.example.arafat.sqlite00;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

//@SuppressWarnings("all")
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    SQLiteDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TextView textView = findViewById(R.id.text);
        ListView listView = findViewById(R.id.list_view);
        ArrayList<String> myList = new ArrayList<>();
        /*myList.add("arafat");
        myList.add("kamal");
        myList.add("tamzid");*/



        try {
            // database create
            myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
            //DROP TABLE
            myDatabase.execSQL("DROP TABLE users");
            // create table
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR, age INT(3))");
            //insert data
            myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('arafat', 25)");

            @SuppressLint("Recycle") Cursor cursor = myDatabase.rawQuery("SELECT * FROM users", null);

            int nameIndex = cursor.getColumnIndex("name");
            //int ageIndex = cursor.getColumnIndex("age");

            cursor.moveToFirst();
            //noinspection InfiniteLoopStatement
            while (!cursor.equals(null)) {
                Log.d(TAG, "onCreate: " + cursor.getString(nameIndex));
                Log.i("lol", "onCreate: " + cursor.getString(nameIndex));
                //textView.setText(cursor.getString(nameIndex));
                myList.add(cursor.getString(nameIndex));
                cursor.moveToNext();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
        listView.setAdapter(arrayAdapter);



    }
}
