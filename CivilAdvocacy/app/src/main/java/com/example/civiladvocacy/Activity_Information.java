package com.example.civiladvocacy;

import android.content.ActivityNotFoundException;
import java.io.IOException;


import android.os.Bundle;

import android.text.style.ForegroundColorSpan;
import android.text.SpannableString;
import java.io.ObjectInputStream;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.view.View;
import android.net.Uri;
import android.widget.Button;
import android.content.pm.PackageManager;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;




public class Activity_Information extends AppCompatActivity {
    static TextView top_bar;

    TextView name_of_the_official;
    ImageView party_image_of_the_official;

    TextView address_of_the_official;

    TextView email_of_the_official;
    TextView website_of_the_official;
    TextView contact_of_the_official;

    private Picasso picasso;
    private boolean picture_availability = false;

    ImageView image_of_the_official;
    Button fb_button;
    TextView party_of_the_official;
    Button twttr_button;
    TextView position_of_the_official;
    Button yt_button;

    static Official current_official;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_info);
        party_image_of_the_official = findViewById(R.id.officiallogo_layout);
        name_of_the_official = findViewById(R.id.officialname_layout);
        address_of_the_official = findViewById(R.id.officialaddress_layout);
        image_of_the_official = findViewById(R.id.officialphoto_layout);
        top_bar = findViewById(R.id.information_topbar_layout);
        email_of_the_official = findViewById(R.id.officialemail_layout);
        fb_button = findViewById(R.id.facebookbutton_layout);
        twttr_button = findViewById(R.id.twitterbutton_layout);
        contact_of_the_official = findViewById(R.id.officialcontact_layout);
        party_of_the_official = findViewById(R.id.officialparty_layout);
        website_of_the_official = findViewById(R.id.officialwebsite_layout);
        yt_button = findViewById(R.id.youtubebutton_layout);
        position_of_the_official = findViewById(R.id.officilalposition_layout);

        picasso = Picasso.get();

        setTitle(MainActivity.getAddress_to_be_added());

        if(current_official != null){

            View root = party_of_the_official.getRootView();

            if (current_official.getPart_of_the_official().equalsIgnoreCase("(Republican Party)"))
            {
                root.setBackgroundColor(getResources().getColor(R.color.red, this.getTheme()));
                party_image_of_the_official.setImageResource(R.drawable.rep_logo);
            }

            else if( current_official.getPart_of_the_official().equalsIgnoreCase("(Democratic Party)") )
            {
                root.setBackgroundColor(getResources().getColor(R.color.blue, this.getTheme()));
                party_image_of_the_official.setImageResource(R.drawable.dem_logo);
            }
            else{
                root.setBackgroundColor(getResources().getColor(R.color.black, this.getTheme()));
                party_image_of_the_official.setVisibility(View.GONE);
            }

            loadOfficial();
        }

    }

    public void loadOfficial(){
        party_of_the_official.setText(current_official.getPart_of_the_official());

        position_of_the_official.setText(current_official.getPosition_of_the_official());
        name_of_the_official.setText(current_official.getName_of_the_official());

        //////////////////////////////////

        if(current_official.getPhotoURL_of_the_official().equalsIgnoreCase("Unknown")){
            picture_availability = true;
        }
        if(current_official.getEmail_of_the_official().equals("Unknown")){
            email_of_the_official.setVisibility(View.GONE);
        }
        else{
            email_of_the_official.setText(new StringBuilder().append(getString(R.string.email)).append(" ").append(current_official.getEmail_of_the_official()).toString());
            Linkify.addLinks(email_of_the_official, Linkify.ALL);
            email_of_the_official.setLinkTextColor(getResources().getColor(R.color.white));
        }

        if (current_official.getSocialLink()[0] == null){
            fb_button.setVisibility(View.GONE);
        }

        if(current_official.getWebsite_of_the_official().equals("Unknown")){
            website_of_the_official.setVisibility(View.GONE);
        }
        else{
            website_of_the_official.setText(new StringBuilder().append(getString(R.string.website)).append(" ").append(current_official.getWebsite_of_the_official()).toString());
            Linkify.addLinks(website_of_the_official, Linkify.ALL);
            website_of_the_official.setLinkTextColor(getResources().getColor(R.color.white));
        }




        if(current_official.getSocialLink()[1] == null){
            twttr_button.setVisibility(View.GONE);
        }
        if(current_official.getAddress_of_the_official().equals("Unknown")){
            address_of_the_official.setVisibility(View.GONE);
        }
        else{

            SpannableString officialAddr = new SpannableString( getString(R.string.address) + " " + current_official.getAddress_of_the_official());
            officialAddr.setSpan(new UnderlineSpan(), 0,officialAddr.length(), 0);
            officialAddr.setSpan(new ForegroundColorSpan(0xFFFFFFFF), 0, officialAddr.length(), 0);
            address_of_the_official.setText(officialAddr);
            address_of_the_official.setClickable(true);

        }
        if(current_official.getSocialLink()[2] == null){
            yt_button.setVisibility(View.GONE);
        }
        if(current_official.getContact_of_the_official().equals("Unknown")){
            contact_of_the_official.setVisibility(View.GONE);
        }else{
            contact_of_the_official.setText(new StringBuilder().append(getString(R.string.phone)).append(" ").append(current_official.getContact_of_the_official()).toString());
            Linkify.addLinks(contact_of_the_official, Linkify.ALL);
            contact_of_the_official.setLinkTextColor(getResources().getColor(R.color.white));
        }
        picasso.load(current_official.getPhotoURL_of_the_official())
                .error(R.drawable.missing)
                .placeholder(R.drawable.placeholder)
                .into(image_of_the_official);

    }



    public void onPhotoClick(View view) {
        if(!picture_availability){//tis will check if the picture is available or not
            Activity_Photo.setCurrent(current_official);
            Intent intent = new Intent(this, Activity_Photo.class);
            startActivity(intent);
        }
    }
    public static void setCurrentOfficial(Official o){
        current_official = o; //this will set a value for the present official
    }
    public void onClickParty(View view){
        String url;
        Intent intent = new Intent();

        if( current_official.getPart_of_the_official().equalsIgnoreCase("(Democratic Party)") ){
            url = "https://democrats.org";
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
        }
        else {
            url = "https://www.gop.com";
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
        }
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void facebookClicked(View v) {
        String FACEBOOK_URL = "https://www.facebook.com/" + current_official.getSocialLink()[0].substring( current_official.getSocialLink()[0].indexOf(",")+1 );;
        String urlToUse;
        PackageManager packageManager = getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                ObjectInputStream.GetField channels = null;
                urlToUse = "fb://page/" + channels.get("Facebook", 0);
            }
        } catch (PackageManager.NameNotFoundException | IOException e) {
            urlToUse = FACEBOOK_URL; //normal web url
        }
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        facebookIntent.setData(Uri.parse(urlToUse));
        startActivity(facebookIntent);
    }
    public void youTubeClicked(View v) {
        String name = current_official.getSocialLink()[2].substring( current_official.getSocialLink()[2].indexOf(",")+1 );
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + name));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/" + name)));
        }
    }
    public void twitterClicked(View v) {
        Intent intent = null;
        String name = current_official.getSocialLink()[1].substring( current_official.getSocialLink()[1].indexOf(",")+1 );
        try {
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name));
        }
        startActivity(intent);
    }



    public void onClickAddress(View view){
        String geo = "geo:0,0?q=";
        String address = current_official.getAddress_of_the_official().replace(" ", "+");
        Intent searchAddress = new Intent(Intent.ACTION_VIEW, Uri.parse(geo+address));
        startActivity(searchAddress);
    }
    public static String getAddress(){
        return top_bar.getText().toString();
    }
    @Override
    public void onBackPressed(){
        current_official = null;
        finish();
    }


    public void setTitle(String s){
        top_bar.setText(s);
    }

}
