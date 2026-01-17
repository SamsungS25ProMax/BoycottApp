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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<Product> categoryList;

    public CategoryAdapter(Context context, List<Product> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Reuse the item_history.xml layout
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Product product = categoryList.get(position);

        holder.tvProductName.setText(product.getName());
        holder.tvCompanyName.setText(product.getCompanyName());
        holder.imgProduct.setImageResource(product.getImageResId());

        if (product.isBoycotted()) {
            holder.tvStatus.setText("Status: Boycotted");
            holder.tvStatus.setTextColor(Color.parseColor("#EF4444"));
        } else {
            holder.tvStatus.setText("Status: Safe");
            holder.tvStatus.setTextColor(Color.parseColor("#10B981"));
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("PRODUCT_BARCODE", product.getBarcodeId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvCompanyName, tvStatus;
        ImageView imgProduct;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvCompanyName = itemView.findViewById(R.id.tvCompanyName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            imgProduct = itemView.findViewById(R.id.imgProduct);
        }
    }
}