package com.manh.petshopdemo1.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.manh.petshopdemo1.DetailProduct;
import com.manh.petshopdemo1.R;
import com.manh.petshopdemo1.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemSearchAdapter extends RecyclerView.Adapter<ItemSearchAdapter.ViewHolder> {
    private List<Product> productList;
    Context context;

    public ItemSearchAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @Override
    public ItemSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.lv_item_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemSearchAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        StringBuilder name_item = new StringBuilder(productList.get(position).getName());
//        if (name_item.length() > 40) {
//            name_item.delete(40, name_item.length());
//
//        }
//        String name = name_item.toString().trim() + "...";
        holder.tv_name_item.setText(name_item.toString());
        String price=String.format("%,d",productList.get(position).getPrice())+"đ";
        holder.tv_price.setText(price);
        Picasso.get().load(productList.get(position).getImage()).resize(180,200).into(holder.image_item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DetailProduct.class);
                intent.putExtra("id",productList.get(position).getId());
                intent.putExtra("name",productList.get(position).getName());
                intent.putExtra("image",productList.get(position).getImage());
                intent.putExtra("price",price);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_item;
        TextView tv_name_item;
        TextView tv_price;

        public ViewHolder(View itemView) {
            super(itemView);
            image_item = itemView.findViewById(R.id.image_item);
            tv_name_item = itemView.findViewById(R.id.tv_name_item);
            tv_price = itemView.findViewById(R.id.tv_price_item);
        }
    }
}
