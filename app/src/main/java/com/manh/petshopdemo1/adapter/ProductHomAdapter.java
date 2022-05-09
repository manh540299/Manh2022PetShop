package com.manh.petshopdemo1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.manh.petshopdemo1.model.Product;
import com.manh.petshopdemo1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductHomAdapter extends RecyclerView.Adapter<ProductHomAdapter.viewHolder> {
    private List<Product> productList;

    public ProductHomAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public ProductHomAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lv_product_home, parent, false);
        viewHolder vh = new viewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ProductHomAdapter.viewHolder holder, int position) {
        Picasso.get().load(productList.get(position).getImage()).resize(120,120).into(holder.imageView);
        StringBuilder name=new StringBuilder(productList.get(position).getName());
        if(name.length()>20){
          name.delete(20,name.length());
        }
        String txtname=name.toString().trim()+"...";
        holder.tvname.setText(txtname);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvname;

        public viewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            tvname = itemView.findViewById(R.id.tvname);
        }
    }
}
