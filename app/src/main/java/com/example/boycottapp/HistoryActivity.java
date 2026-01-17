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

import com.example.boycottapp.adapter.HistoryAdapter;
import com.example.boycottapp.database.MockDatabase;
import com.example.boycottapp.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 2. SETUP RECYCLER VIEW (The new part!)
        recyclerView = findViewById(R.id.recyclerViewBookingQueue);

        // This tells the list to arrange items vertically (like a standard list)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get data from your MockDatabase (Assuming you have a method to get a list)
        // If you don't have getAllProducts(), see the note below *
        List<Product> historyList = MockDatabase.getSessionHistory();

        adapter = new HistoryAdapter(this, historyList);
        recyclerView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.nav_history);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Intent intent = null;
                if (itemId == R.id.nav_history) {
                    return true;
                } else if (itemId == R.id.nav_favorites) {
                    startActivity(new Intent(HistoryActivity.this, BookmarkListActivity.class));
                    overridePendingTransition(0, 0); // Stops the blinking animation
                    return true;
                } else if (itemId == R.id.nav_about) {
                    startActivity(new Intent(HistoryActivity.this, AboutUsActivity.class));
                    overridePendingTransition(0, 0); // Stops the blinking animation
                    return true;
                } else if (itemId == R.id.nav_home) {
                    startActivity(new Intent(HistoryActivity.this, HomeActivity.class));
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
}
