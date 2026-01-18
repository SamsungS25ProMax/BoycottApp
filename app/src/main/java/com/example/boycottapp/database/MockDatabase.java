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

        // --- BOYCOTTED ---
        productList.add(new Product("955100001", "Coca-Cola", "The Coca-Cola Company", "Food_Beverages", true, R.drawable.cocacola, "Operates a factory in the illegal Israeli settlement of Atarot.", "Try: 100Plus","100plus"));
        productList.add(new Product("955100002", "Starbucks", "Starbucks Coffee Company", "Food_Beverages", true, R.drawable.starbucks, "Sued its union for a pro-Palestine post; maintains strong Zionist ties.", "Try: ZUS Coffee","ZUS Coffee"));
        productList.add(new Product("955100003", "Maggi", "Nestlé", "Food_Beverages", true, R.drawable.maggi, "Nestlé owns 100% of Osem, a major Israeli food manufacturer.", "Try: Mamee","Mamee Chef"));

// --- ALTERNATIVES (Malaysian) ---
        productList.add(new Product("955100004", "100PLUS", "F&N", "Food_Beverages", false, R.drawable.hundredplus, "F&N denies direct business with Israel and emphasizes its commitment to local markets and sentiments, differentiating itself from products directly linked to supporting Israel. ", "Safe to use.","100plus"));
        productList.add(new Product("955100005", "ZUS Coffee", "ZUS Coffee Malaysia", "Food_Beverages", false, R.drawable.zus, "Popular local Malaysian coffee chain.", "Safe to use.","100plus"));
        productList.add(new Product("955100006", "Mamee Chef", "Mamee-Double Decker", "Food_Beverages", false, R.drawable.mamee, "Iconic Malaysian brand for instant noodles.", "Safe to use.","100plus"));

        // ==========================================
        // CATEGORY 2: BEAUTY_CARE
        // ==========================================
        // --- BOYCOTTED ---
        productList.add(new Product("955100019", "L'Oréal", "L'Oréal Group", "Beauty_Care", true, R.drawable.loreal, "Has long-standing investments and manufacturing sites in Israel.", "Try: Safi","Safi"));
        productList.add(new Product("955100020", "Garnier", "L'Oréal Group", "Beauty_Care", true, R.drawable.garnier, "Part of the L'Oréal group's boycott target.", "Try: Wardah","Wardah"));
        productList.add(new Product("955100021", "Maybelline", "L'Oréal Group", "Beauty_Care", true, R.drawable.maybelline, "Under the L'Oréal umbrella, which supports the Israeli economy.", "Try: Silkygirl","Silkygirl"));

// --- ALTERNATIVES (Malaysian/Regional) ---
        productList.add(new Product("955100022", "Safi", "Wipro Unza (Malaysia)", "Beauty_Care", false, R.drawable.safi, "Iconic Malaysian Halal beauty brand.", "Safe to use.","100plus"));
        productList.add(new Product("955100023", "Wardah", "PT Paragon Technology", "Beauty_Care", false, R.drawable.wardah, "Very popular Halal-certified alternative in Malaysia.", "Safe to use.","100plus"));
        productList.add(new Product("955100024", "Silkygirl", "Alliance Cosmetics Sdn Bhd", "Beauty_Care", false, R.drawable.silkygirl, "A home-grown Malaysian brand for cosmetics.", "Safe to use.","100plus"));

        // ==========================================
        // CATEGORY 3: HOME_LIVING
        // ==========================================
        // --- BOYCOTTED ---
        productList.add(new Product("955100013", "HP", "Hewlett-Packard", "Home_Living", true, R.drawable.hp, "Provides technology that helps maintain Israeli checkpoints.", "Try: Brother","Brother"));
        productList.add(new Product("955100014", "Puma", "Puma SE", "Home_Living", true, R.drawable.puma, "Sponsors the Israel Football Association which includes settlement teams.", "Try: Al-Ikhsan","Al-Ikhsan"));
        productList.add(new Product("955100015", "SodaStream", "SodaStream Ltd.", "Home_Living", true, R.drawable.sodastream, "An Israeli company with a history of operating in occupied territory.", "Try: Cuckoo","Cuckoo"));

// --- ALTERNATIVES (Malaysian/Safe) ---
        productList.add(new Product("955100016", "Brother", "Brother International (M)", "Home_Living", false, R.drawable.brother, "Reliable Japanese brand widely used in Malaysia.", "Safe to use.","100plus"));
        productList.add(new Product("955100017", "Al-Ikhsan", "Al-Ikhsan Sports Sdn Bhd", "Home_Living", false, R.drawable.alikhsan, "Malaysia's #1 sports retailer for gear and apparel.", "Safe to use.","100plus"));
        productList.add(new Product("955100018", "Cuckoo", "Cuckoo International (M)", "Home_Living", false, R.drawable.cuckoo, "Trusted provider of home appliances in Malaysia.", "Safe to use.","100plus"));

        // ==========================================
        // CATEGORY 4: HEALTH_WELLNESS
        // ==========================================
        // --- BOYCOTTED ---
        productList.add(new Product("955100007", "Colgate", "Colgate-Palmolive", "Health_Wellness", true, R.drawable.colgate, "Provides significant financial and R&D support to Israeli institutions.", "Try: Mukmin","Mukmin"));
        productList.add(new Product("955100008", "Oral-B", "Procter & Gamble", "Health_Wellness", true, R.drawable.oralb, "Parent company P&G has deep economic investments in Israel.", "Try: Systema","Systema"));
        productList.add(new Product("955100009", "Pampers", "Procter & Gamble", "Health_Wellness", true, R.drawable.pampers, "Significant corporate investment in the Israeli tech and economy sectors.", "Try: PetPet","PetPet"));

// --- ALTERNATIVES (Malaysian) ---
        productList.add(new Product("955100010", "Mukmin", "Al-Meswak Mu'min Sdn Bhd", "Health_Wellness", false, R.drawable.mukmin, "Malaysian Halal toothpaste brand.", "Safe to use.","100plus"));
        productList.add(new Product("955100011", "Systema", "Southern Lion (Sdn Bhd)", "Health_Wellness", false, R.drawable.systema, "Widely available and trusted local choice.", "Safe to use.","100plus"));
        productList.add(new Product("955100012", "PetPet", "Disposable Soft Goods (M) Sdn Bhd", "Health_Wellness", false, R.drawable.petpet, "Reliable Malaysian-made baby diaper brand.", "Safe to use.","100plus"));
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