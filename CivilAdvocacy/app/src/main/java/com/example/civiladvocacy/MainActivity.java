package com.example.civiladvocacy;
import android.util.Log;
import android.os.Bundle;
import com.google.android.gms.location.FusedLocationProviderClient;
import android.widget.EditText;
import java.io.IOException;
import android.location.Address;
import android.view.Gravity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.widget.TextView;
import android.net.NetworkInfo;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import androidx.appcompat.app.AlertDialog;
import java.util.ArrayList;
import android.annotation.SuppressLint;
import java.util.List;
import android.location.Location;
import android.net.ConnectivityManager;
import android.view.View;
import androidx.annotation.NonNull;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.location.LocationServices;
import java.util.Locale;
import androidx.core.app.ActivityCompat;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import android.view.Menu;
import android.location.Geocoder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final ArrayList<Official> array_of_officials = new ArrayList<>();
    private static final String TAG = "";
    private static final int LOCATION_REQUEST = 111;
    static RecyclerView recycler_of_officials;
    private final String googleCivic_API = "https://www.googleapis.com/civicinfo/v2/representatives?key=AIzaSyCu7pJWXC9253NoRg_GjoSB-_ZaJ81LUag&address=";

// using api key number 1:   AIzaSyCu7pJWXC9253NoRg_GjoSB-_ZaJ81LUag
    private static String address_to_be_added = "";
    public boolean error_of_the_location = false;
    private String link_of_the_api = "";


    private String state = "KS";
    static TextView top_bar;
    private boolean internet_network_connection = true;
    private FusedLocationProviderClient mFusedLocationClient;
    private String loc = "Unknown";

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: " + savedInstanceState.getString("api"));
        link_of_the_api = savedInstanceState.getString("api");
        Official_Downloader.downloadOfficials(this, link_of_the_api);
    }
    public void onClickMainMenuItem(MenuItem i){
        if(internet_network_connection){
            if(i.getItemId() == R.id.about_menu) {
                Intent intent = new Intent(this, Activity_About.class);
                startActivity(intent);
            }
            else if (i.getItemId() == R.id.loaction_menu) {
                starting_dialogue_of_location();
            }
        }
        else {
            Toast.makeText(this, getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        }

    }
    public void official_addition_to_the_array(Official o){
        array_of_officials.add(o);
    }
    public void building_the_URL(String a) {
        address_to_be_added = a;
        address_to_be_added = address_to_be_added.replace(" ", "%20");
        changeStateToInitial(address_to_be_added);
        address_to_be_added = address_to_be_added.replace("East", "E");
        address_to_be_added = address_to_be_added.replace("North", "N");
        address_to_be_added = address_to_be_added.replace("South", "S");
        address_to_be_added = address_to_be_added.replace("West", "W");

        link_of_the_api = googleCivic_API + address_to_be_added;
    }
    private boolean checking_for_location_permission(){
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
                    new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, LOCATION_REQUEST);
            return false;
        }
        return true;
    }
    public void loc_error(){
        top_bar.setText(getString(R.string.no_loc));
        error_of_the_location = true;
        Toast.makeText( this, getString(R.string.invalid), Toast.LENGTH_LONG).show();
    }

    @SuppressLint("MissingPermision")
    private void determining_the_location() {
        if (checking_for_location_permission()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                return;
            }
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    Log.d(TAG, "determineLocation: location :::::: "+location.toString().replace("fused","gps"));
                    loc = getting_place(location);

                    String[] locationResult = loc.split("\n");
                    Log.d(TAG, "determineLocation: Location : "+locationResult[0]);
                    array_of_officials.clear();
                    building_the_URL(locationResult[0]);
                    Official_Downloader.downloadOfficials(MainActivity.this , link_of_the_api);
                }
            })
                    .addOnFailureListener(this, e -> Toast.makeText(this,
                            e.getMessage(), Toast.LENGTH_LONG).show());
        }
    }
    public void loading_the_recycler(){
        recycler_of_officials = findViewById(R.id.recyclerMain);
        Official_Adapter officialAdapter = new Official_Adapter(array_of_officials, this);
        recycler_of_officials.setAdapter(officialAdapter);
        recycler_of_officials.setLayoutManager(new LinearLayoutManager(this));
    }
    private boolean internet_connection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("api", link_of_the_api);
        Log.d(TAG, "onSaveInstanceState: " + link_of_the_api);

        super.onSaveInstanceState(outState);
    }


    public void starting_dialogue_of_location(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText et1 = new EditText(this);
        et1.setGravity(Gravity.CENTER_HORIZONTAL);
        builder.setMessage( getString(R.string.new_loc_dialog) );
        builder.setView(et1);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String ans = et1.getText().toString();

                if(ans.isEmpty() || ans == null){
                    Toast.makeText(MainActivity.this, R.string.invalid, Toast.LENGTH_SHORT).show();
                }
                else {
                    array_of_officials.clear();

                    building_the_URL(ans);
                    Official_Downloader.downloadOfficials(MainActivity.this , link_of_the_api);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private String getting_place(Location loc) {

        StringBuilder sb = new StringBuilder();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            sb.append(String.format(
                    Locale.getDefault(),
                    "%s, %s%n%nProvider: %s%n%n%.5f, %.5f",
                    city, state, loc.getProvider(), loc.getLatitude(), loc.getLongitude()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recycler_of_officials = findViewById(R.id.recyclerMain);
        top_bar = findViewById(R.id.top_bar_main);


        loading_the_recycler();

        setTitle(getString(R.string.enter_loc));
        array_of_officials.clear();


        internet_network_connection = internet_connection();
        if (!internet_network_connection){
            setTitle(getString(R.string.no_network));
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        determining_the_location();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == LOCATION_REQUEST){
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    determining_the_location();
                } else {
                    Log.d(TAG, "onRequestPermissionsResult: Location permission was denied - cannot determine address");
                }
            }
        }
    }








    @Override
    public void onClick(View view) {

        int index = recycler_of_officials.getChildLayoutPosition(view);

        Activity_Information.setCurrentOfficial( array_of_officials.get(index) );

        Intent intent = new Intent(this, Activity_Information.class);
        startActivity(intent);
    }






    public void changeStateToInitial(String s){
        String [] states = {"Alabama", "Alaska", "Alberta", "American Samoa", "Arizona", "Arkansas", "Armed Forces AE", "Armed Forces Americas", "Armed Forces Pacific", "British Columbia", "California", "Colorado", "Connecticut", "Delaware", "District Of Columbia", "Florida", "Georgia", "Guam", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Manitoba", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Brunswick", "New Hampshire", "New Jersey", "New Mexico", "New York", "Newfoundland", "North Carolina", "North Dakota", "Northwest Territories", "Nova Scotia", "Nunavut", "Ohio", "Oklahoma", "Ontario", "Oregon", "Pennsylvania", "Prince Edward Island", "Puerto Rico", "Quebec", "Rhode Island", "Saskatchewan", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virgin Islands", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming", "Yukon Territory"};
        String [] initials = {"AL", "AK", "AB", "AS", "AZ", "AR", "AE", "AA", "AP", "BC", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "GU", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MB", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NB", "NH", "NJ", "NM", "NY", "NF", "NC", "ND", "NT", "NS", "NU", "OH", "OK", "ON", "OR", "PA", "PE", "PR", "QC", "RI", "SK", "SC", "SD", "TN", "TX", "UT", "VT", "VI", "VA", "WA", "WV", "WI", "WY", "YT"};

        for(int i= 0; i < states.length; i++) {
            if(s.equals(states[i])){
                s = s.replace(states[i], initials[i]);
            }
        }

    }

    public void setTitle(String s){
        top_bar.setText(s);
    }






    public static String getAddress_to_be_added(){
        return top_bar.getText().toString();
    }







}