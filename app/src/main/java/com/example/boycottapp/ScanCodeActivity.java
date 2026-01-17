package com.example.boycottapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ScanCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton backButton = findViewById(R.id.icon_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Intent intent = null;
                if (itemId == R.id.nav_history) {
                    startActivity(new Intent(ScanCodeActivity.this, HistoryActivity.class));
                    overridePendingTransition(0, 0); // <--- Add this for smoothness
                    return true;
                } else if (itemId == R.id.nav_favorites) {
                    startActivity(new Intent(ScanCodeActivity.this, BookmarkListActivity.class));
                    overridePendingTransition(0, 0); // <--- Add this for smoothness
                    return true;
                } else if (itemId == R.id.nav_about) {
                    startActivity(new Intent(ScanCodeActivity.this, AboutUsActivity.class));
                    overridePendingTransition(0, 0); // <--- Add this for smoothness
                    return true;
                } else if (itemId == R.id.nav_home) {
                    startActivity(new Intent(ScanCodeActivity.this, HomeActivity.class));
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
    }
}
