package com.denniscode.shareit;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;

public class UrlManager {
    private SharedUrlDAO sharedUrlDAO;

    public UrlManager(Context context){
        // Initialize the SharedUrlDAO
        sharedUrlDAO = new SharedUrlDAO(context);
    }
    public ArrayList<String> getUrls() {
        sharedUrlDAO.open();
        ArrayList<String> sharedUrls = sharedUrlDAO.getAllSharedUrls();
        sharedUrlDAO.close();
        // Return the list of shared URLs from the database
        return sharedUrls;
    }

    public boolean deleteUrl(String url) {
        // Delete the URL from the database
        sharedUrlDAO.open();
        sharedUrlDAO.deleteSharedUrl(url);
        sharedUrlDAO.close();
        return true;
    }

    public void copyUrl(String url, Context context) {
        // Copy the URL to the clipboard
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("URL", url);
        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(clipData);
        }
    }

    public long handleSharedUrlIntent(Intent intent) {
        String action = intent.getAction();
        String type = intent.getType();
        long insertId = -1;

        if (Intent.ACTION_SEND.equals(action) && type != null && "text/plain".equals(type)) {
            // Get the shared URL
            String sharedUrl = intent.getStringExtra(Intent.EXTRA_TEXT);
            // Save the shared URL to the database
            if (sharedUrl != null) {
                sharedUrlDAO.open();
                insertId = sharedUrlDAO.insertSharedUrl(sharedUrl);
                sharedUrlDAO.close();
            }
        }
        return insertId;
    }

    public void addClipboardUrl(Context context) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null && clipboard.hasPrimaryClip()) {
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            String copiedText = item.getText().toString();

            if (TextUtil.isValidURL(copiedText)) {
                sharedUrlDAO.open();
                sharedUrlDAO.insertSharedUrl(copiedText);
                sharedUrlDAO.close();
            }
        }

    }
}
