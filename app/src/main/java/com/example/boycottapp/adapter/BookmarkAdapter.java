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

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {

    private Context context;
    private List<Product> favoriteList;

    public BookmarkAdapter(Context context, List<Product> favoriteList) {
        this.context = context;
        this.favoriteList = favoriteList;
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Reusing your existing design!
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new BookmarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        Product product = favoriteList.get(position);

        holder.tvProductName.setText(product.getName());
        holder.tvCompanyName.setText(product.getCompanyName());
        holder.imgProduct.setImageResource(product.getImageResId());

        // Logic to color code the status
        if (product.isBoycotted()) {
            holder.tvStatus.setText("Status: Boycotted");
            holder.tvStatus.setTextColor(Color.parseColor("#EF4444")); // Red
        } else {
            holder.tvStatus.setText("Status: Safe");
            holder.tvStatus.setTextColor(Color.parseColor("#10B981")); // Green
        }

        // When clicking a favorite item, go to details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            // Make sure your Product model has getBarcode() or getBarcodeId()
            intent.putExtra("PRODUCT_BARCODE", product.getBarcodeId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public static class BookmarkViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvCompanyName, tvStatus;
        ImageView imgProduct;

        public BookmarkViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            imgProduct = itemView.findViewById(R.id.imgProduct);
        }
    }
}