package com.example.containerprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentDatabase extends SQLiteOpenHelper {

    // Database name
    private static final String DATABASE_NAME = "StudentDB";

    // Database version
    private static final int DATABASE_VERSION = 1;

    // Table name
    public static final String TABLE_NAME = "students";

    // Constructor
    public StudentDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create table
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE students (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT)"
        );
    }

    // Upgrade database
    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        db.execSQL(
                "DROP TABLE IF EXISTS students"
        );

        onCreate(db);
    }
}