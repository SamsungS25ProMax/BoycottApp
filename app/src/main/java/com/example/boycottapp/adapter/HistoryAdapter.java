package com.example.boycottapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boycottapp.ProductDetailActivity;
import com.example.boycottapp.R;
import com.example.boycottapp.model.Product;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<Product> productList;
    private Context context;

    public HistoryAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This tells the adapter to use your "item_history.xml" design
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Product product = productList.get(position);

        // 1. Set simple text and images
        holder.tvProductName.setText(product.getName());
        holder.tvCompanyName.setText(product.getCompanyName());
        holder.imgProduct.setImageResource(product.getImageResId());

        // Assuming your Product model has a getCompany() method.
        // If not, you can hardcode it or add it to your model.
        // holder.tvCompanyName.setText(product.getManufacturer());

        // 2. Handle Logic (Red for Boycott, Green for Safe)
        if (product.isBoycotted()) {
            holder.tvStatus.setText("Status: Boycotted");
            holder.tvStatus.setTextColor(Color.parseColor("#EF4444")); // Red
        } else {
            holder.tvStatus.setText("Status: Safe");
            holder.tvStatus.setTextColor(Color.parseColor("#10B981")); // Green
        }

        // 3. Make the item clickable to see details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            // Pass the unique ID or Barcode so the detail screen knows what to load
            intent.putExtra("PRODUCT_BARCODE", product.getBarcodeId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // This class holds the UI elements for one row
    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvCompanyName, tvStatus;
        ImageView imgProduct;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            imgProduct = itemView.findViewById(R.id.imgProduct);
        }
    }
}
