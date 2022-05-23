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

import com.manh.petshopdemo1.ItemList;
import com.manh.petshopdemo1.model.Menu;
import com.manh.petshopdemo1.R;
import com.manh.petshopdemo1.model.Product;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.viewholder> {
    List<Menu> menuList;
    Context context;

    public MenuAdapter(List<Menu> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.lv_menu_home, parent,false);
        viewholder vh = new viewholder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.text.setText(menuList.get(position).getTetx());
        holder.img.setImageResource(menuList.get(position).getImage());
        holder.itemView.setOnClickListener(view -> {
            Intent intent=new Intent(context, ItemList.class);
            intent.putExtra("itemview",menuList.get(position).getTetx());
            intent.putExtra("key",menuList.get(position).getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView img;

        public viewholder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.tvtext);
            img = itemView.findViewById(R.id.imgimage);
        }
    }
    public void release(){
        context=null;
    }
}
