package com.example.civiladvocacy;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import android.os.Bundle;
public class Activity_Photo extends AppCompatActivity {

    TextView top_bar;
    TextView pos_of_the_official;
    TextView name_of_the_official;
    ImageView photo_of_the_official;
    ImageView img_of_the_party;
    private Picasso picasso;
    static Official current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        img_of_the_party = findViewById(R.id.party_of_the_official_layout);

        top_bar = findViewById(R.id.topbar_of_the_official_layout);
        photo_of_the_official = findViewById(R.id.photo_of_the_official_layout);
        pos_of_the_official = findViewById(R.id.position_of_the_official_layout);
        name_of_the_official = findViewById(R.id.name_of_the_official_layout_);

        picasso = Picasso.get();

        loadData();

    }

    public static void setCurrent( Official o ){
        current = o;
    }

    public void loadData(){
        top_bar.setText( Activity_Information.getAddress() );
        pos_of_the_official.setText(current.getPosition_of_the_official());
        name_of_the_official.setText(current.getName_of_the_official());


        View root = pos_of_the_official.getRootView();//we call tthis to see the backgroundColor


        if( current.getPart_of_the_official().equalsIgnoreCase("(Democratic Party)") ){
            root.setBackgroundColor(getResources().getColor(R.color.blue, this.getTheme()));
            img_of_the_party.setImageResource(R.drawable.dem_logo);
        }
        else if (current.getPart_of_the_official().equalsIgnoreCase("(Republican Party)")){
            root.setBackgroundColor(getResources().getColor(R.color.red, this.getTheme()));
            img_of_the_party.setImageResource(R.drawable.rep_logo);
        }
        else {
            root.setBackgroundColor(getResources().getColor(R.color.black, this.getTheme()));
            img_of_the_party.setVisibility(View.GONE);
        }

        picasso.load(current.getPhotoURL_of_the_official())
                .error(R.drawable.brokenimage)
                .placeholder(R.drawable.placeholder)
                .into(photo_of_the_official);


    }



    @Override
    public void onBackPressed(){
        finish();
    }
}
