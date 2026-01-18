package com.example.boycottapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

// --- CRITICAL IMPORT FIXES ---
import com.example.boycottapp.database.MockDatabase;
import com.example.boycottapp.model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class ProductDetailActivity extends AppCompatActivity {

    private Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Intent intent = null;
                if (itemId == R.id.nav_history) {
                    startActivity(new Intent(ProductDetailActivity.this, HistoryActivity.class));
                    overridePendingTransition(0, 0); // <--- Add this for smoothness
                    return true;
                } else if (itemId == R.id.nav_favorites) {
                    startActivity(new Intent(ProductDetailActivity.this, BookmarkListActivity.class));
                    overridePendingTransition(0, 0); // <--- Add this for smoothness
                    return true;
                } else if (itemId == R.id.nav_about) {
                    startActivity(new Intent(ProductDetailActivity.this, AboutUsActivity.class));
                    overridePendingTransition(0, 0); // <--- Add this for smoothness
                    return true;
                } else if (itemId == R.id.nav_home) {
                    startActivity(new Intent(ProductDetailActivity.this, HomeActivity.class));
                    overridePendingTransition(0, 0); // <--- Add this for smoothness
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

        // 1. Get Barcode
        String barcode = getIntent().getStringExtra("PRODUCT_BARCODE");

        // 2. Find in DB
        currentProduct = MockDatabase.findByBarcode(barcode);

        if (currentProduct == null) {
            Toast.makeText(this, "Error: Product not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // This saves the product to your temporary history list
        MockDatabase.addToHistory(currentProduct);

        setupUI();
    }

    private void setupUI() {
        View topBg = findViewById(R.id.topBackground);
        TextView tvStatus = findViewById(R.id.tvRecommended);
        TextView tvName = findViewById(R.id.tvItemName);
        TextView tvReason = findViewById(R.id.tvReason);
        TextView tvCompanyName = findViewById(R.id.tvCompanyName);
        ImageView imgProduct = findViewById(R.id.productImage);
        ImageView ivStatusIcon = findViewById(R.id.ivClose);
        MaterialCardView reasonCard = findViewById(R.id.redCard);
        Switch favToggle = findViewById(R.id.favoriteToggle);
        MaterialButton btnAlt = findViewById(R.id.btnAlternative);
        ImageButton btnBack = findViewById(R.id.icon_back);

        tvName.setText(currentProduct.getName());
        tvCompanyName.setText(currentProduct.getCompanyName());
        imgProduct.setImageResource(currentProduct.getImageResId());

        if (currentProduct.isBoycotted()) {
            // RED STATE
            topBg.setBackgroundColor(0xFFE91E1E);
            tvStatus.setText("Product is on Boycott List");
            ivStatusIcon.setImageResource(R.drawable.ic_close_circle_red);
            reasonCard.setCardBackgroundColor(0xFFEB5757);
            tvReason.setText(currentProduct.getReason());
            btnAlt.setVisibility(View.VISIBLE);
        } else {
            // GREEN STATE
            topBg.setBackgroundColor(0xFF4CAF50);
            tvStatus.setText("Product is Safe to Buy");
            ivStatusIcon.setImageResource(android.R.drawable.checkbox_on_background);
            reasonCard.setCardBackgroundColor(0xFF66BB6A);
            tvReason.setText("This product is verified safe. No boycott calls found.");
            btnAlt.setVisibility(View.GONE);
        }

        favToggle.setChecked(currentProduct.isBookmarked());
        favToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentProduct.setBookmarked(isChecked);
            if(isChecked) Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show();
        });

        btnBack.setOnClickListener(v -> finish());

        btnAlt.setOnClickListener(v -> {
            // 1. Check if the product is boycotted
            if (currentProduct.isBoycotted()) {

                // 2. Identify the alternative name (e.g., "Life Cola")
                // Using your getAlternative() method which stores strings like "Try: 100Plus"
                // or you can use a specific getter if you added alternativeName to your model
                String altName = currentProduct.getAlternative();

                // 3. Create Intent using 'ProductDetailActivity.this' instead of 'context'
                Intent intent = new Intent(ProductDetailActivity.this, AlternativeActivity.class);

                // 4. Pass the alternative identifier to the next page
                intent.putExtra("ALT_NAME_KEY", altName);

                startActivity(intent);
            } else {
                Toast.makeText(this, "This product is already safe!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}