package com.denniscode.shareit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "shared_urls.db";
    private static final int DATABASE_VERSION = 1;

    // Table name and column names
    public static final String TABLE_SHARED_URLS = "shared_urls";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_URL = "url";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the shared_urls table
        String createTableQuery = "CREATE TABLE " + TABLE_SHARED_URLS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_URL + " TEXT NOT NULL UNIQUE)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHARED_URLS);
        onCreate(db);
    }
}