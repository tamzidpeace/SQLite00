package com.example.arafat.sqlite00;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TextView textView = findViewById(R.id.text);
        listView = findViewById(R.id.list_view);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        ArrayList<String> myList = new ArrayList<>();


        try {
            // database create
            myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
            //DROP TABLE
            //myDatabase.execSQL("DROP TABLE users");
            // create table
            myDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name VARCHAR)");

            //insert data
            //myDatabase.execSQL(" INSERT INTO users(name) VALUES('ARAFAT') ");
            insertData();

            //delete data
            deleteData();

            //update
            updateData();

            Cursor cursor = myDatabase.rawQuery("SELECT * FROM users", null);

            int nameIndex = cursor.getColumnIndex("name");
            int idIndex = cursor.getColumnIndex("id");

            cursor.moveToFirst();
            //noinspection InfiniteLoopStatement
            while (true) {
                Log.d(TAG, "onCreate: " + cursor.getString(nameIndex));
                Log.d(TAG, "onCreate: " + cursor.getInt(idIndex));
                String mId = String.valueOf(cursor.getInt(idIndex));
                Toast.makeText(this, mId, Toast.LENGTH_SHORT).show();
                myList.add(cursor.getString(nameIndex) + mId);
                cursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
        arrayAdapter.notifyDataSetChanged();
        listView.setAdapter(arrayAdapter);

    }

    //insert data
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
                        if (!mText.isEmpty()) {
                            myDatabase.execSQL(" INSERT INTO users(name) VALUES('" + mText + "') ");
                            // refresh the activity
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                            Log.d(TAG, "onClick: " + "data added");
                        }
                    }
                });
                alertDialog.show();
            }
        });
    }

    // delete data

    private void deleteData() {

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d(TAG, "onItemLongClick: id is: " + id + "position is: " + position);
                int mId = (int) id + 1;
                myDatabase.execSQL("DELETE FROM users WHERE id = '" + mId + "' ");
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                return true;
            }
        });

    }

    private void updateData() {
        final EditText input = new EditText(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Weight");
                alertDialog.setMessage("You forgot to enter your weight!");
                alertDialog.setView(input);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do something when the user presses OK (place focus on weight input?)
                        String mText = input.getText().toString();
                        int mId = (int) id + 1;
                        if (!mText.isEmpty()) {
                            myDatabase.execSQL(" UPDATE users SET name = '"+mText+"' WHERE id = '"+mId+"' ");
                            // refresh the activity
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                            Log.d(TAG, "onClick: " + "data added");
                        }
                    }
                });
                alertDialog.show();
            }
        });
    }
}
