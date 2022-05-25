package com.manh.petshopdemo1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.manh.petshopdemo1.model.Messenge;
import com.manh.petshopdemo1.R;

import java.util.List;

public class MessengerAdapter extends RecyclerView.Adapter<MessengerAdapter.ViewHolder> {
    private List<Messenge> messengeList;

    public MessengerAdapter(List<Messenge> messengeList) {
        this.messengeList = messengeList;
        notifyDataSetChanged();
    }

    @Override
    public MessengerAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_messenge_fragment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MessengerAdapter.ViewHolder holder, int position) {
     holder.messenge.setText(messengeList.get(position).getMessenge());
    }

    @Override
    public int getItemCount() {
        return messengeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView messenge;
        public ViewHolder(View itemView) {
            super(itemView);
            messenge=itemView.findViewById(R.id.tvmess);
        }
    }
}
