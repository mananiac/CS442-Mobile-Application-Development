package com.example.civiladvocacy;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;



public class Official_ViewHolder extends RecyclerView.ViewHolder {
    TextView name_of_the_official;

    TextView title_of_the_official;

    public Official_ViewHolder(View view){
        super(view);
        title_of_the_official = view.findViewById(R.id.title_of_the_official_layout);
        name_of_the_official = view.findViewById(R.id.name_of_the_official_layout);
    }

}