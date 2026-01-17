package com.example.boycottapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boycottapp.adapter.BookmarkAdapter;
import com.example.boycottapp.database.MockDatabase;
import com.example.boycottapp.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class BookmarkListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookmarkAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();
        // We load data in onResume() so that if you un-favorite something
        // in the detail page and come back, the list updates automatically!
        loadFavorites();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_list);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 2. Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerViewBookingQueue);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Data loading happens in onResume()

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.nav_favorites);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Intent intent = null;
                if (itemId == R.id.nav_history) {
                    startActivity(new Intent(BookmarkListActivity.this, HistoryActivity.class));
                    overridePendingTransition(0, 0); // Stops the blinking animation
                    return true;
                } else if (itemId == R.id.nav_favorites) {
                    return true;
                } else if (itemId == R.id.nav_about) {
                    startActivity(new Intent(BookmarkListActivity.this, AboutUsActivity.class));
                    overridePendingTransition(0, 0); // Stops the blinking animation
                    return true;
                } else if (itemId == R.id.nav_home) {
                    startActivity(new Intent(BookmarkListActivity.this, HomeActivity.class));
                    overridePendingTransition(0, 0); // Stops the blinking animation
                    return true;
                }

                // Check if an intent was created
                if (intent != null) {
                    // This is the Magic Line:
                    // It says "If this screen is already open in the background, bring it to the front
                    // instead of opening a brand new copy."
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                    startActivity(intent);
                    overridePendingTransition(0, 0); // smooth no-animation switch
                    return true;
                }
                return false;
            }
        });
    }

    private void loadFavorites() {
        // Get only the bookmarked items
        List<Product> favList = MockDatabase.getFavorites();
        adapter = new BookmarkAdapter(this, favList);
        recyclerView.setAdapter(adapter);
    }
}
