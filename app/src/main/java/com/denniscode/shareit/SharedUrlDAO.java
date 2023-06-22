package com.denniscode.shareit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class SharedUrlDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public SharedUrlDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }
    public void open(){database = dbHelper.getWritableDatabase();}
    public void close() {
        dbHelper.close();
    }

    public long insertSharedUrl(String url) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_URL, url);
        return database.insert(DatabaseHelper.TABLE_SHARED_URLS, null, values);
    }

    public void deleteSharedUrl(String url) {
        String whereClause = DatabaseHelper.COLUMN_URL + " = ?";
        String[] whereArgs = { url };
        database.delete(DatabaseHelper.TABLE_SHARED_URLS, whereClause, whereArgs);
    }

    public ArrayList<String> getAllSharedUrls() {
        ArrayList<String> sharedUrls = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_SHARED_URLS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int urlIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_URL);
            do {
                String url = cursor.getString(urlIndex);
                sharedUrls.add(url);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return sharedUrls;
    }
}
