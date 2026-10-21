package com.example.firebasecurdop;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentProvider extends ContentProvider {

    public static final String AUTHORITY =
            "com.example.firebasecurdop.provider";

    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/students");

    private DatabaseReference databaseReference;

    @Override
    public boolean onCreate() {

        databaseReference =
                FirebaseDatabase.getInstance()
                        .getReference("students");

        return true;
    }

    // INSERT student into Firebase
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        if (values == null) {
            return null;
        }

        String name = values.getAsString("name");
        String course = values.getAsString("course");
        String rollNo = values.getAsString("rollNo");

        String studentId = databaseReference.push().getKey();

        if (studentId != null) {

            Student student =
                    new Student(name, course, rollNo);

            databaseReference
                    .child(studentId)
                    .setValue(student);
        }

        return Uri.withAppendedPath(CONTENT_URI, studentId);
    }

    // Query is kept for ContentProvider compatibility.
    // Firebase data will be displayed using DisplayActivity.
    @Nullable
    @Override
    public Cursor query(
            @NonNull Uri uri,
            @Nullable String[] projection,
            @Nullable String selection,
            @Nullable String[] selectionArgs,
            @Nullable String sortOrder) {

        MatrixCursor cursor = new MatrixCursor(
                new String[]{"name", "course", "rollNo"}
        );

        return cursor;
    }

    @Override
    public int delete(
            @NonNull Uri uri,
            @Nullable String selection,
            @Nullable String[] selectionArgs) {

        return 0;
    }

    @Override
    public int update(
            @NonNull Uri uri,
            @Nullable ContentValues values,
            @Nullable String selection,
            @Nullable String[] selectionArgs) {

        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.dir/vnd." + AUTHORITY + ".students";
    }
}