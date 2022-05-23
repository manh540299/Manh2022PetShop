package com.manh.petshopdemo1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.manh.petshopdemo1.R;
import com.manh.petshopdemo1.interf.IOnClickPurchase;
import com.manh.petshopdemo1.model.Purchase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder> {
    List<Purchase> purchases;
    IOnClickPurchase iOnClickPurchase;

    public PurchaseAdapter(List<Purchase> purchases, IOnClickPurchase iOnClickPurchase) {
        this.purchases = purchases;
        this.iOnClickPurchase = iOnClickPurchase;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.lv_purchases,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(purchases.get(position).getImage()).resize(200,200).into(holder.imgProduct);
        holder.tvnameProduct.setText(purchases.get(position).getName());
        holder.tvsize.setText(purchases.get(position).getSize());
        holder.tvquantity.setText("x"+String.valueOf(purchases.get(position).getQuantity()));
        holder.tvtotal.setText(String.format("%,d",purchases.get(position).getTotal())+"đ");
        holder.tvprice.setText(String.format("%,d",purchases.get(position).getTotal())+"đ");
        holder.tvallitem.setText(purchases.get(position).getQuantityAllItem()+" item");
        holder.itemView.setOnClickListener(view -> iOnClickPurchase.OnClickPurchase(purchases.get(holder.getAdapterPosition())));
    }

    @Override
    public int getItemCount() {
        return purchases.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProduct;
        private TextView tvnameProduct;
        private TextView tvsize;
        private TextView tvquantity;
        private TextView tvtotal;
        private TextView tvprice;
        private TextView tvallitem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct=itemView.findViewById(R.id.imgproduct);
            tvnameProduct=itemView.findViewById(R.id.tv_purchases_name_product);
            tvsize=itemView.findViewById(R.id.tv_purchases_size);
            tvquantity=itemView.findViewById(R.id.tvpurchases_quantity);
            tvtotal=itemView.findViewById(R.id.tvpurchases_total);
            tvprice=itemView.findViewById(R.id.tvpurchases_price);
            tvallitem=itemView.findViewById(R.id.tvpurchases_quantity_all_item);
        }
    }
}
