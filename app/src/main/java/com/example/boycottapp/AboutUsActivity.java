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

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.nav_about);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Intent intent = null;
                if (itemId == R.id.nav_history) {
                    startActivity(new Intent(AboutUsActivity.this, HistoryActivity.class));
                    overridePendingTransition(0, 0); // Stops the blinking animation
                    return true;
                } else if (itemId == R.id.nav_favorites) {
                    startActivity(new Intent(AboutUsActivity.this, BookmarkListActivity.class));
                    overridePendingTransition(0, 0); // Stops the blinking animation
                    return true;
                } else if (itemId == R.id.nav_about) {
                    return true;
                } else if (itemId == R.id.nav_home) {
                    startActivity(new Intent(AboutUsActivity.this, HomeActivity.class));
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
