package com.manh.petshopdemo1.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.manh.petshopdemo1.DetailProduct;
import com.manh.petshopdemo1.model.ItemSale;
import com.manh.petshopdemo1.model.Product;
import com.manh.petshopdemo1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductHomAdapter extends RecyclerView.Adapter<ProductHomAdapter.viewHolder> {
    private List<ItemSale> itemSales;
    private Context context;

    public ProductHomAdapter(List<ItemSale> itemSales, Context context) {
        this.itemSales = itemSales;
        this.context = context;
    }

    @Override
    public ProductHomAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lv_product_home, parent, false);
        viewHolder vh = new viewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ProductHomAdapter.viewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(itemSales.get(position).getImage()).resize(120, 120).into(holder.imageView);
        StringBuilder name = new StringBuilder(itemSales.get(position).getName());
        String price = String.format("%,d", itemSales.get(position).getPrice()) + "đ";
        String sale = String.format("%,d", itemSales.get(position).getSale()) + "đ";
        holder.tvPrice.setText(price);
        holder.tvPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvSale.setText(sale);
        holder.tvname.setText(name.toString());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailProduct.class);
            intent.putExtra("name", itemSales.get(position).getName());
            intent.putExtra("image", itemSales.get(position).getImage());
            intent.putExtra("price", sale);
            intent.putExtra("sale",price);
            context.startActivity(intent);
        });

    }

    public void release() {
        context = null;
    }

    @Override
    public int getItemCount() {
        return itemSales.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tvname;
        private TextView tvSale;
        private TextView tvPrice;

        public viewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            tvname = itemView.findViewById(R.id.tvname);
            tvSale=itemView.findViewById(R.id.tvsale);
            tvPrice=itemView.findViewById(R.id.tvprice);
        }
    }
}
