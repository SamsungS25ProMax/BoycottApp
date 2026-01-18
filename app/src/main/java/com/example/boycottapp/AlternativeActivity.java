package com.example.boycottapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.boycottapp.database.MockDatabase;
import com.example.boycottapp.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

public class AlternativeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ensure this layout matches your Alternative UI (likely activity_alternative)
        setContentView(R.layout.activity_alternative);

        // 1. Setup Bottom Navigation (Matching your ProductDetailActivity logic)
        setupNavigation();

        // 2. Get the "Key" passed from the previous activity
        // Example: If user clicked Alt on Coca-Cola, this might be "Try: 100Plus" or "100Plus"
        String altSearchKey = getIntent().getStringExtra("ALT_NAME_KEY");

        if (altSearchKey == null || altSearchKey.isEmpty()) {
            Toast.makeText(this, "No alternative data found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 3. Search for the Product in MockDatabase
        // We clean the string in case it contains "Try: " prefix
        Product alternativeProduct = findAlternativeInDatabase(altSearchKey);

        if (alternativeProduct != null) {
            displayProductDetails(alternativeProduct);
        } else {
            Toast.makeText(this, "Details for " + altSearchKey + " not available yet", Toast.LENGTH_LONG).show();
        }

        // 4. Back Button Logic
        ImageButton btnBack = findViewById(R.id.icon_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    private Product findAlternativeInDatabase(String key) {
        // Clean the key: "Try: 100Plus" becomes "100Plus"
        String cleanKey = key.replace("Try: ", "").trim().toLowerCase();

        for (Product p : MockDatabase.productList) {
            if (p.getName().toLowerCase().contains(cleanKey) ||
                    p.getCompanyName().toLowerCase().contains(cleanKey)) {
                return p;
            }
        }
        return null;
    }



    private void displayProductDetails(Product product) {
        TextView tvName = findViewById(R.id.tvItemName);
        TextView tvCompanyName = findViewById(R.id.tvCompanyName);
        TextView tvReason = findViewById(R.id.tvReason);
        ImageView imgProduct = findViewById(R.id.productImage);
        Switch favToggle = findViewById(R.id.favoriteToggle);

        // Update basic details
        if (tvName != null) tvName.setText(product.getName());
        if (tvCompanyName != null) tvCompanyName.setText(product.getCompanyName());
        if (tvReason != null) tvReason.setText(product.getReason());
        if (imgProduct != null) imgProduct.setImageResource(product.getImageResId());

        // Handle the Bookmark/Favorite Toggle
        if (favToggle != null) {
            // Set initial state based on the database
            favToggle.setChecked(product.isBookmarked());

            favToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
                product.setBookmarked(isChecked);
                if (isChecked) {
                    Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setupNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        if (bottomNavigationView == null) return;

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_history) {
                startActivity(new Intent(this, HistoryActivity.class));
            } else if (itemId == R.id.nav_favorites) {
                startActivity(new Intent(this, BookmarkListActivity.class));
            } else if (itemId == R.id.nav_about) {
                startActivity(new Intent(this, AboutUsActivity.class));
            } else if (itemId == R.id.nav_home) {
                startActivity(new Intent(this, HomeActivity.class));
            }
            overridePendingTransition(0, 0);
            return true;
        });
    }
}