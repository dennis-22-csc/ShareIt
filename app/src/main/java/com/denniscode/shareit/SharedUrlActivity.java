package com.denniscode.shareit;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SharedUrlActivity extends AppCompatActivity {
    private UrlManager urlManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urlManager = new UrlManager(this);
        long insertedUrlId = urlManager.handleSharedUrlIntent(getIntent());
        if (insertedUrlId != -1) {
            Toast.makeText(this, "URL saved successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "URL failed to Save", Toast.LENGTH_LONG).show();
        }
        finish();
    }

}
