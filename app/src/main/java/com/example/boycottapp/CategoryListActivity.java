package com.example.boycottapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boycottapp.adapter.CategoryAdapter;
import com.example.boycottapp.database.MockDatabase;
import com.example.boycottapp.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class CategoryListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // We reuse your foodnbev layout for ALL categories
        setContentView(R.layout.activity_category_foodnbev);

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Get the data passed from HomeActivity
        String categoryKey = getIntent().getStringExtra("CATEGORY_KEY");   // e.g. "Food_Beverages"
        String displayTitle = getIntent().getStringExtra("CATEGORY_TITLE"); // e.g. "Food & Beverage"

        // 2. Update the Title on screen
        TextView titleTv = findViewById(R.id.text_title);
        if (displayTitle != null) {
            titleTv.setText(displayTitle);
        }

        // 3. Set up Back Button
        ImageButton btnBack = findViewById(R.id.icon_back);
        btnBack.setOnClickListener(v -> finish());

        // 4. Setup RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewBookingQueue);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 5. Get Data from Database
        List<Product> categoryProducts = MockDatabase.getByCategory(categoryKey);

        if (categoryProducts.isEmpty()) {
            Toast.makeText(this, "No products found in this category", Toast.LENGTH_SHORT).show();
        }

        CategoryAdapter adapter = new CategoryAdapter(this, categoryProducts);
        recyclerView.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Intent intent = null;
                if (itemId == R.id.nav_history) {
                    startActivity(new Intent(CategoryListActivity.this, HistoryActivity.class));
                    overridePendingTransition(0, 0); // Stops the blinking animation
                    return true;
                } else if (itemId == R.id.nav_favorites) {
                    startActivity(new Intent(CategoryListActivity.this, BookmarkListActivity.class));
                    overridePendingTransition(0, 0); // Stops the blinking animation
                    return true;
                } else if (itemId == R.id.nav_about) {
                    startActivity(new Intent(CategoryListActivity.this, AboutUsActivity.class));
                    overridePendingTransition(0, 0); // Stops the blinking animation
                    return true;
                } else if (itemId == R.id.nav_home) {
                    startActivity(new Intent(CategoryListActivity.this, HomeActivity.class));
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