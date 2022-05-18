package com.manh.petshopdemo1.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.manh.petshopdemo1.R;
import com.manh.petshopdemo1.interf.OnClickItemListener;
import com.manh.petshopdemo1.model.TypeItem;

import java.util.ArrayList;
import java.util.List;

public class SizeItemAdapter extends RecyclerView.Adapter<SizeItemAdapter.ViewHolder> {
    private List<TypeItem> typeItemList;
    private int pos = 0;
    private Context context;
    private final ArrayList<Integer> select = new ArrayList<>();
    private OnClickItemListener onClickItemListener;

    public SizeItemAdapter(List<TypeItem> typeItemList,Context context, OnClickItemListener onClickItemListener) {
        this.typeItemList = typeItemList;
        this.context = context;
        this.onClickItemListener = onClickItemListener;
    }

    @Override
    public SizeItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lvsize, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(SizeItemAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvtype.setText(typeItemList.get(position).getName());
        if (pos == 0) {
            select.add(0);
        }
        if (!select.contains(position)) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.smoke_color));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.greenn1));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onClickItemListener.onClickItem(typeItemList.get(position));
                if(position!=0)
                    pos=1;
                if (select.isEmpty()) {
                    select.add(position);

                } else {
                    select.clear();
                    select.add(position);
                    notifyDataSetChanged();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return typeItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvtype;

        public ViewHolder(View itemView) {
            super(itemView);
            tvtype = itemView.findViewById(R.id.tvtype);
            itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.smoke_color));

        }

    }





}
