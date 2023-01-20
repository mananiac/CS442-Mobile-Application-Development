package com.example.civiladvocacy;
import java.util.ArrayList;

import android.view.LayoutInflater;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import android.view.View;



public class Official_Adapter extends RecyclerView.Adapter<Official_ViewHolder> {
    private ArrayList<Official> array_of_officials = new ArrayList<>();
    private final MainActivity Activity_main;

    public Official_Adapter(ArrayList<Official> o, MainActivity m){
        this.array_of_officials = o;
        this.Activity_main = m;
    }
    @Override
    public void onBindViewHolder(@NonNull Official_ViewHolder holder, int position) {
        Official o = array_of_officials.get(position);
        holder.title_of_the_official.setText( o.getPosition_of_the_official() );
        String temp = o.getName_of_the_official() + " " + o.getPart_of_the_official();
        holder.name_of_the_official.setText( temp );
    }
    @NonNull
    @Override
    public Official_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gov_offical_item, parent, false);
        itemView.setOnClickListener(Activity_main);
        return new Official_ViewHolder(itemView);
    }




    @Override
    public int getItemCount() {
        return array_of_officials.size();
    }
}
