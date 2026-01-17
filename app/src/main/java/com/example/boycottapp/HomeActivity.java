package com.example.boycottapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.boycottapp.database.MockDatabase;
import com.example.boycottapp.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MockDatabase.loadData(); // Initialize DB

        android.widget.EditText searchInput = findViewById(R.id.et_search_input);
        android.widget.ImageButton searchBtn = findViewById(R.id.btn_search_action);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchInput.getText().toString().trim();

                if (query.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Please enter a product name", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 1. Try Barcode
                Product foundProduct = MockDatabase.findByBarcode(query);

                // 2. Try Name
                if (foundProduct == null) {
                    List<Product> results = MockDatabase.searchByName(query);
                    if (!results.isEmpty()) {
                        foundProduct = results.get(0);
                    }
                }

                // 3. Launch Result
                if (foundProduct != null) {
                    Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
                    intent.putExtra("PRODUCT_BARCODE", foundProduct.getBarcodeId());
                    startActivity(intent);
                } else {
                    Toast.makeText(HomeActivity.this, "Product not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        MaterialButton scanButton = findViewById(R.id.btn_scan_camera);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ScanCodeActivity.class));
            }
        });

        // === CATEGORY BUTTONS SETUP ===

        // Button 1: Food & Beverage
        findViewById(R.id.btn_category_1).setOnClickListener(v -> openCategory("Food_Beverages", "Food & Beverage"));

        // Button 2: Health & Wellness
        findViewById(R.id.btn_category_2).setOnClickListener(v -> openCategory("Health_Wellness", "Health & Wellness"));

        // Button 3: Home & Living
        findViewById(R.id.btn_category_3).setOnClickListener(v -> openCategory("Home_Living", "Home & Living"));

        // Button 4: Beauty & Care
        findViewById(R.id.btn_category_4).setOnClickListener(v -> openCategory("Beauty_Care", "Beauty & Care"));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Intent intent = null;
                if (itemId == R.id.nav_history) {
                    startActivity(new Intent(HomeActivity.this, HistoryActivity.class));
                    overridePendingTransition(0, 0); // Stops the blinking animation
                    return true;
                } else if (itemId == R.id.nav_favorites) {
                    startActivity(new Intent(HomeActivity.this, BookmarkListActivity.class));
                    overridePendingTransition(0, 0); // Stops the blinking animation
                    return true;
                } else if (itemId == R.id.nav_about) {
                    startActivity(new Intent(HomeActivity.this, AboutUsActivity.class));
                    overridePendingTransition(0, 0); // Stops the blinking animation
                    return true;
                } else if (itemId == R.id.nav_home) {
                    // You are already in HomeActivity, no action needed
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

    private void openCategory(String key, String title) {
        Intent intent = new Intent(HomeActivity.this, CategoryListActivity.class);
        intent.putExtra("CATEGORY_KEY", key);
        intent.putExtra("CATEGORY_TITLE", title);
        startActivity(intent);
    }
}
