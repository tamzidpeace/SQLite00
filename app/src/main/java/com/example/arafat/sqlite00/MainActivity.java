package com.example.arafat.sqlite00;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//@SuppressWarnings("all")
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    SQLiteDatabase myDatabase;
    static int a = 10;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TextView textView = findViewById(R.id.text);
        ListView listView = findViewById(R.id.list_view);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        ArrayList<String> myList = new ArrayList<>();


        try {
            // database create
            myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
            //DROP TABLE
            //myDatabase.execSQL("DROP TABLE users");
            // create table
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (id INT(3), name VARCHAR,PRIMARY KEY(id))");
            //insert data
            insertData();
            //myDatabase.execSQL("INSERT INTO users (name, age) VALUES ('arafat', 25)");

            @SuppressLint("Recycle") Cursor cursor = myDatabase.rawQuery("SELECT * FROM users", null);

            int nameIndex = cursor.getColumnIndex("name");
            //int ageIndex = cursor.getColumnIndex("age");

            cursor.moveToFirst();
            //noinspection InfiniteLoopStatement
            while (true) {
                Log.d(TAG, "onCreate: " + cursor.getString(nameIndex));
                myList.add(cursor.getString(nameIndex));
                cursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
        arrayAdapter.notifyDataSetChanged();
        listView.setAdapter(arrayAdapter);

    }

    private void insertData() {

        //myDatabase.execSQL(" INSERT INTO users(name) VALUES('ARAFAT') ");
        final EditText input = new EditText(this);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Weight");
                alertDialog.setMessage("You forgot to enter your weight!");
                alertDialog.setView(input);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do something when the user presses OK (place focus on weight input?)
                        String mText = input.getText().toString();
                        myDatabase.execSQL(" INSERT INTO users(name) VALUES('"+mText+"') ");
                        Log.d(TAG, "onClick: " + "data added");
                    }
                });
                //alertDialog.setIcon(R.drawable.icon);
                alertDialog.show();
            }
        });
    }
}
