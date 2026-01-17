package com.example.boycottapp.database;

import com.example.boycottapp.R;
import com.example.boycottapp.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MockDatabase {
    // The main list that holds all data
    public static List<Product> productList = new ArrayList<>();

    // Run this ONCE in your MainActivity onCreate
    public static void loadData() {
        if (!productList.isEmpty()) return; // Prevent duplicate data
        // ==========================================
        // CATEGORY 1: FOOD_BEVERAGES
        // ==========================================

        // 1. Coca-Cola (Boycotted)
        productList.add(new Product(
                "955100004",
                "Coca-Cola",
                "The Coca-Cola Company",
                "Food_Beverages",           // UNDERSCORE UPDATED
                true,                       // isBoycotted = TRUE
                R.drawable.cocacola,
                "Coca-Cola has supported Israel since 1966, defying the Arab League boycott.",
                "Try: Life Cola (Safe Local Brand)"
        ));

        // 2. Life Cola (Safe Alternative)
        productList.add(new Product(
                "955100005",
                "Life Cola",
                "Koperasi Malaysia",
                "Food_Beverages",
                false,
                R.drawable.cocacola,
                "Safe. Local Malaysian brand produced by Koperasi.",
                "N/A"
        ));

        // 3. Starbucks
        productList.add(new Product(
                "955100001",
                "Starbucks Coffee",
                "Starbucks Corp",
                "Food_Beverages",
                true,
                android.R.drawable.ic_menu_camera,
                "Parent company supports operations in conflicting zones.",
                "Try: Zus Coffee, Gigi Coffee"
        ));

        // 4. McDonald's
        productList.add(new Product(
                "955100002",
                "McDonald's",
                "McDonald's Corp",
                "Food_Beverages",
                true,
                android.R.drawable.ic_menu_camera,
                "Controversial funding allegations linked to parent corporation.",
                "Try: Ramly Burger"
        ));

        // 5. Farm Fresh
        productList.add(new Product(
                "955100003",
                "Farm Fresh Milk",
                "Farm Fresh Berhad",
                "Food_Beverages",
                false,
                android.R.drawable.ic_menu_camera,
                "Safe. Malaysian-owned local brand.",
                "N/A"
        ));

        // ==========================================
        // CATEGORY 2: BEAUTY_CARE
        // ==========================================
        productList.add(new Product(
                "955200001",
                "L'Oreal Shampoo",
                "L'Oreal Group",
                "Beauty_Care",
                true,
                android.R.drawable.ic_menu_camera,
                "Major shareholder contributes to displacement activities.",
                "Try: Safi, Sunsilk"
        ));

        productList.add(new Product(
                "955200002",
                "Safi Face Wash",
                "Wipro Unza",
                "Beauty_Care",
                false,
                android.R.drawable.ic_menu_camera,
                "Safe. Local Halal-certified brand.",
                "N/A"
        ));

        // ==========================================
        // CATEGORY 3: HOME_LIVING
        // ==========================================
        productList.add(new Product(
                "955300001",
                "Dynamo Detergent",
                "Procter & Gamble (P&G)",
                "Home_Living",
                true,
                android.R.drawable.ic_menu_camera,
                "Owned by P&G, a major target of global boycotts.",
                "Try: Daia, Top"
        ));

        productList.add(new Product(
                "955300002",
                "Daia Detergent",
                "Gentle Supreme",
                "Home_Living",
                false,
                android.R.drawable.ic_menu_camera,
                "Safe. Widely available local alternative.",
                "N/A"
        ));

        // ==========================================
        // CATEGORY 4: HEALTH_WELLNESS
        // ==========================================
        productList.add(new Product(
                "955400001",
                "Vicks VapoRub",
                "Procter & Gamble (P&G)",
                "Health_Wellness",
                true,
                android.R.drawable.ic_menu_camera,
                "Owned by P&G. See boycott list for details.",
                "Try: Minyak Kapak"
        ));

        productList.add(new Product(
                "955400002",
                "Panadol",
                "GSK (GlaxoSmithKline)",
                "Health_Wellness",
                false,
                android.R.drawable.ic_menu_camera,
                "Safe. Standard pharmaceutical essentials.",
                "N/A"
        ));
    }

    // --- HELPERS ---
    public static List<Product> searchByName(String query) {
        List<Product> results = new ArrayList<>();
        for (Product p : productList) {
            if (p.getName().toLowerCase().contains(query.toLowerCase())) {
                results.add(p);
            }
        }
        return results;
    }

    public static List<Product> getByCategory(String category) {
        List<Product> results = new ArrayList<>();
        for (Product p : productList) {
            // Updated to be case-insensitive but strict on format
            // "Food_Beverages" matches "food_beverages"
            if (p.getCategory().equalsIgnoreCase(category)) {
                results.add(p);
            }
        }
        return results;
    }

    public static Product findByBarcode(String barcode) {
        for (Product p : productList) {
            if (p.getBarcodeId().equals(barcode)) {
                return p;
            }
        }
        return null;
    }

    public static List<Product> getAllProducts() {
        // If the list is empty, try loading data automatically
        if (productList.isEmpty()) {
            loadData();
        }
        return new ArrayList<>(productList);
    }

    // 1. Add this new list at the top
    public static List<Product> sessionHistory = new ArrayList<>();

    // 2. Add this helper method
    public static void addToHistory(Product product) {
        // Optional: Avoid duplicates (so Coke doesn't appear twice)
        if (sessionHistory.contains(product)) {
            sessionHistory.remove(product);
        }
        // Add to the TOP of the list (index 0) so the newest is first
        sessionHistory.add(0, product);
    }

    // 3. Add a getter for the history page
    public static List<Product> getSessionHistory() {
        return new ArrayList<>(sessionHistory);
    }

    public static List<Product> getFavorites() {
        List<Product> favs = new ArrayList<>();
        // Check every product in our database
        for (Product p : productList) {
            // If the user bookmarked it, add to the list
            if (p.isBookmarked()) {
                favs.add(p);
            }
        }
        return favs;
    }
}