package com.example.midtermproj;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchPlacesAPI {

    public static final String PLACES_API= "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?";
    public static final String INPUT = "input=";
    public static final String INPUT_TYPE = "inputtype";
    public static final String FIELDS = "fields=";
    public static final String API_KEY="key=";

    static String getInfo(String queryString)
    {
        HttpURLConnection urlConnection = null;
        String results = null;
        BufferedReader reader = null;
        String url;

        try{

            url = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input="+queryString+
                    "&inputtype=textquery&fields=geometry&key=AIzaSyDqg2W4eCUfCyn0VSu-JkoXlBMrQzNDMFs";

            URL requestURL = new URL(url);

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.connect();

            // Get the InputStream.
            InputStream inputStream = urlConnection.getInputStream();

            // Create a buffered reader from that input stream.
            reader = new BufferedReader(new InputStreamReader(inputStream));

            // Use a StringBuilder to hold the incoming response.
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                // Since it's JSON, adding a newline isn't necessary (it won't
                // affect parsing) but it does make debugging a *lot* easier
                // if you print out the completed buffer for debugging.
                builder.append("\n");
                if (builder.length() == 0) {
                    // Stream was empty. No point in parsing.
                    return null;
                }
                results = builder.toString();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
        }
        return results;
    }
}
