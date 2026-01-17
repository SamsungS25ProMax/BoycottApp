package com.example.boycottapp.model;

public class Product {
    private String barcodeId;
    private String name;
    private String companyName;
    private String category;
    private boolean isBoycotted; // true = RED (Boycott), false = GREEN (Safe)
    private boolean isBookmarked;
    private int imageResId;      // The ID of the image in your drawable folder
    private String reason;       // Why is it boycotted?
    private String alternative;  // What should I buy instead?

    public Product(String barcodeId, String name, String companyName, String category, boolean isBoycotted, int imageResId, String reason, String alternative) {
        this.barcodeId = barcodeId;
        this.name = name;
        this.companyName = companyName;
        this.category = category;
        this.isBoycotted = isBoycotted;
        this.imageResId = imageResId;
        this.reason = reason;
        this.alternative = alternative;
        this.isBookmarked = false; // Default to false
    }

    // --- GETTERS ---
    public String getCompanyName() { return companyName; }
    public String getBarcodeId() { return barcodeId; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public boolean isBoycotted() { return isBoycotted; }
    public int getImageResId() { return imageResId; }
    public String getReason() { return reason; }
    public String getAlternative() { return alternative; }
    public boolean isBookmarked() { return isBookmarked; }

    // --- SETTERS ---
    public void setBookmarked(boolean bookmarked) { isBookmarked = bookmarked; }
}
