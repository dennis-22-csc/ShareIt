package com.denniscode.shareit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewUrls;
    private ArrayList<String> sharedUrls;
    private Toolbar toolbar;
    private String url;
    private UrlAdapter adapter;
    private TextView textDashboardView;
    private RelativeLayout contentDashboard;
    private UrlManager urlManager;

    @Override
    protected void onResume() {
        super.onResume();
        urlManager.addClipboardUrl(this);
        sharedUrls = urlManager.getUrls();

        if (sharedUrls.isEmpty()){
            recyclerViewUrls.setVisibility(View.GONE);
            displayEmptyArea();
        } else {
            contentDashboard.setVisibility(View.GONE);
            displayUrls();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        contentDashboard = findViewById(R.id.content_dashboard);
        recyclerViewUrls = findViewById(R.id.recycler_view_urls);
        textDashboardView = findViewById(R.id.text_dashboard);

        urlManager = new UrlManager(this);
        urlManager.addClipboardUrl(this);
        sharedUrls = urlManager.getUrls();

        if (sharedUrls.isEmpty()){
            recyclerViewUrls.setVisibility(View.GONE);
            displayEmptyArea();
        } else {
            contentDashboard.setVisibility(View.GONE);
            displayUrls();
        }

    }

    private void displayEmptyArea(){
        // slide textview
        TextUtil.slideText(textDashboardView);
    }
    private void displayUrls() {

        adapter = new UrlAdapter(sharedUrls);

        // Set up the RecyclerView and adapter
        recyclerViewUrls.setAdapter(adapter);
        recyclerViewUrls.setLayoutManager(new LinearLayoutManager(this));

        // Add DividerItemDecoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        recyclerViewUrls.addItemDecoration(dividerItemDecoration);

        // Implement swipe-to-delete functionality
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // get position of URL
                int position = viewHolder.getAdapterPosition();
                String url = sharedUrls.get(position);

                // delete URL from list
                sharedUrls.remove(position);
                adapter.notifyItemRemoved(position);

                // delete URL from database
                urlManager.deleteUrl(url);
            }
        };

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewUrls);

        // Set item click listener for the RecyclerView
        adapter.setOnItemClickListener(new UrlAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String url = sharedUrls.get(position);

                // Launch an intent to open the URL
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        // Set item long click listener for the RecyclerView
        adapter.setOnItemLongClickListener(new UrlAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {

                url = sharedUrls.get(position);
                // Add a delay of 2 seconds before copying text
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        urlManager.copyUrl(url, MainActivity.this);
                        // Show a toast message
                        Toast.makeText(MainActivity.this, "URL Copied", Toast.LENGTH_SHORT).show();
                    }
                }, 2000); // 2000 milliseconds = 2 seconds
            }
        });
    }

}
