package com.example.civiladvocacy;
import com.android.volley.Request;
import com.android.volley.Response;
import org.json.JSONObject;
import com.android.volley.RequestQueue;
import android.widget.Toast;
import android.util.Log;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;

public class Official_Downloader {
    static MainActivity main_acti;
    private static String main_URL;
    private static final String TAG = "url";

    static RequestQueue queue0;


    public static void downloadOfficials( MainActivity m, String url ){
        main_URL = url;
        main_acti = m;
        queue0 = Volley.newRequestQueue(main_acti);


        Log.d(TAG, "downloadOfficials: url ::::::::::::::" + main_URL);

        Response.Listener<JSONObject> listener =
                response -> parsing_the_JSON(response.toString());

        Response.ErrorListener error =
                error1 -> main_acti.loc_error();


        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, main_URL,
                        null, listener, error);


        queue0.add(jsonObjectRequest);

    }

    private static void parsing_the_JSON(String s) {
        try {

            JSONObject jObjMain = new JSONObject(s);

            JSONObject normalized = jObjMain.getJSONObject("normalizedInput");
            String address =
                    normalized.getString("line1") + " " + normalized.get("city") + " " + normalized.get("state") + " " + normalized.get("zip");


            JSONArray officeIndex = jObjMain.getJSONArray("offices");


            JSONArray officialsArr = jObjMain.getJSONArray("officials");

            for (int i = 0; i < officeIndex.length(); i++){

                JSONObject office = officeIndex.getJSONObject(i);


                String position = office.getString("name");


                JSONArray officialIndicesArr = office.getJSONArray("officialIndices");

                for (int j = 0; j < officialIndicesArr.length(); j++){
                    int index = officialIndicesArr.getInt(j);

                    JSONObject official = officialsArr.getJSONObject(index);

                    String name = official.getString("name");

                     String party;
                    if(official.has("party")){
                        party = official.getString("party");
                    }
                    else{
                        party = "Unknown";
                    }


                    String contact_number_of_the_official;
                    if( official.has("phones") ){
                        JSONArray phoneArr = official.getJSONArray("phones");
                        contact_number_of_the_official = phoneArr.getString(0);
                    }
                    else{
                        contact_number_of_the_official = "Unknown";
                    }

                    String email_of_the_official;
                    if( official.has("emails") ){
                        JSONArray phoneArr = official.getJSONArray("emails");
                        email_of_the_official = phoneArr.getString(0);
                    }
                    else{
                        email_of_the_official = "Unknown";
                    }

                    String official_addr;
                    if(official.has("address")){
                        JSONArray addressArr = official.getJSONArray("address");
                        JSONObject addressOb = addressArr.getJSONObject(0);
                        official_addr = addressOb.getString("line1") + " " + addressOb.getString("city") + " " + addressOb.getString("state") + " " + addressOb.getString("zip");
                    }
                    else{
                        official_addr = "Unknown";
                    }

                    String website;
                    if(official.has("urls")){
                        JSONArray urls = official.getJSONArray("urls");
                        website = urls.getString(0);
                    }
                    else {
                        website = "Unknown";
                    }

                    String pic__of_the_official;
                    if(official.has("photoUrl")){
                        pic__of_the_official = official.getString("photoUrl");
                        pic__of_the_official = pic__of_the_official.replace("http","https");
                    }
                    else{
                        pic__of_the_official = "Unknown";
                    }

                    String [] social_media_array = new String [3];
                    if(official.has("channels")){
                        JSONArray channels = official.getJSONArray("channels");
                        for(int k = 0; k < channels.length(); k++){
                            JSONObject media = channels.getJSONObject(k);
                            String type = media.getString("type");
                            if(type.equalsIgnoreCase("facebook")) {
                                String str = type + "," + media.getString("id");
                                social_media_array[0] = str;
                            }
                            else if (type.equalsIgnoreCase("twitter")) {
                                String str = type + "," + media.getString("id");
                                social_media_array[1] = str;
                            }
                            else if(type.equalsIgnoreCase("youtube")){
                                String str = type + "," + media.getString("id");
                                social_media_array[2] = str;
                            }

                        }
                    }
                    else{
                        for (int k = 0; k < 3; k++){
                            social_media_array[k] = null;
                        }
                    }


                    Official o = new Official(position, name, party, contact_number_of_the_official, email_of_the_official, official_addr , website, pic__of_the_official, social_media_array);

                    main_acti.official_addition_to_the_array(o);

                }

            }

            main_acti.setTitle(address);
            main_acti.loading_the_recycler();

            Toast.makeText(main_acti, main_acti.getString(R.string.new_loc), Toast.LENGTH_SHORT).show();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
