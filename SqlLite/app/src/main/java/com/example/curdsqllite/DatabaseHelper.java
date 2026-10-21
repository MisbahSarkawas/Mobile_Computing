package com.example.curdsqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name
    private static final String DATABASE_NAME = "StudentDB.db";

    // Database version
    private static final int DATABASE_VERSION = 1;

    // Table name
    private static final String TABLE_NAME = "student";


    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Create database and table
    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME +
                " (id INTEGER PRIMARY KEY, " +
                "name TEXT, " +
                "course TEXT)";

        db.execSQL(query);
    }


    // Upgrade database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }


    // =========================
    // INSERT
    // =========================

    public boolean insertStudent(int id, String name, String course) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("id", id);
        values.put("name", name);
        values.put("course", course);

        long result = db.insert(TABLE_NAME, null, values);

        return result != -1;
    }


    // =========================
    // VIEW
    // =========================

    public Cursor getAllStudents() {

        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT * FROM " + TABLE_NAME,
                null
        );
    }


    // =========================
    // UPDATE
    // =========================

    public boolean updateStudent(int id, String name, String course) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("course", course);

        int result = db.update(
                TABLE_NAME,
                values,
                "id=?",
                new String[]{String.valueOf(id)}
        );

        return result > 0;
    }


    // =========================
    // DELETE
    // =========================

    public boolean deleteStudent(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                TABLE_NAME,
                "id=?",
                new String[]{String.valueOf(id)}
        );

        return result > 0;
    }
}