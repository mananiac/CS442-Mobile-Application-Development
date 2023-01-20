package com.example.civiladvocacy;

import android.net.Uri;
import android.view.View;
import android.content.Intent;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Activity_About extends AppCompatActivity {

    TextView googleApiLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        googleApiLink = findViewById(R.id.googleCivicAPI);

    }

    public void onClickAPI(View view){
        Intent intent = new Intent();
        String url = getString(R.string.api);// this will fetch the string
        intent.setAction(Intent.ACTION_VIEW);// this will add an action to the intent
        intent.addCategory(Intent.CATEGORY_BROWSABLE); // this will add a category to the intent
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

}
