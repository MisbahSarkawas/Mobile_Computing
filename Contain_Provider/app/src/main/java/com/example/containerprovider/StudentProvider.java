package com.example.containerprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class StudentProvider extends ContentProvider {

    // Authority
    public static final String AUTHORITY =
            "com.example.containerprovider.provider";

    // Content URI
    public static final Uri CONTENT_URI =
            Uri.parse(
                    "content://" +
                            AUTHORITY +
                            "/students"
            );

    // Database object
    private StudentDatabase database;

    // Create Provider
    @Override
    public boolean onCreate() {

        database =
                new StudentDatabase(getContext());

        return true;
    }

    // Insert data
    @Override
    public Uri insert(
            Uri uri,
            ContentValues values) {

        SQLiteDatabase db =
                database.getWritableDatabase();

        long id =
                db.insert(
                        StudentDatabase.TABLE_NAME,
                        null,
                        values
                );

        if (id == -1) {
            return null;
        }

        Uri newUri =
                Uri.withAppendedPath(
                        CONTENT_URI,
                        String.valueOf(id)
                );

        // Notify data change
        getContext()
                .getContentResolver()
                .notifyChange(
                        uri,
                        null
                );

        return newUri;
    }

    // Read data
    @Override
    public Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {

        SQLiteDatabase db =
                database.getReadableDatabase();

        return db.query(
                StudentDatabase.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    // Update data
    @Override
    public int update(
            Uri uri,
            ContentValues values,
            String selection,
            String[] selectionArgs) {

        SQLiteDatabase db =
                database.getWritableDatabase();

        return db.update(
                StudentDatabase.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }

    // Delete data
    @Override
    public int delete(
            Uri uri,
            String selection,
            String[] selectionArgs) {

        SQLiteDatabase db =
                database.getWritableDatabase();

        return db.delete(
                StudentDatabase.TABLE_NAME,
                selection,
                selectionArgs
        );
    }

    // MIME type
    @Override
    public String getType(Uri uri) {

        return "vnd.android.cursor.dir/vnd.students";
    }
}