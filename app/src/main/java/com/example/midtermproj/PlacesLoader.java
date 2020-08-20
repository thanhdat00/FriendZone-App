package com.example.midtermproj;

import android.content.Context;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlacesLoader extends AsyncTaskLoader<LatLng> {

    private String mQueryString;

    public PlacesLoader(@NonNull Context context, String queryString) {
        super(context);
        mQueryString = queryString;
    }

    @Override
    protected void onStartLoading() {

        //call forceLoad() to start the loadInBackground() method.
        //The loader will not start loading data until you call the forceLoad() method.

        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public LatLng loadInBackground() {

        LatLng result = null;
        String s = FetchPlacesAPI.getInfo(mQueryString);

        try{
            JSONObject jsonObject = new JSONObject(s);

            String isExisted = jsonObject.getString("status");
            if (isExisted.equals("OK")) {
               JSONArray jsonArray = jsonObject.getJSONArray("candidates");
                   JSONObject jsonObject_geometry = jsonArray.getJSONObject(0).getJSONObject("geometry");

               JSONObject tmp = jsonObject_geometry.getJSONObject("location");

                //JSONArray jsonObject_location = jsonObject_geometry.getJSONArray("location");

                String lat = tmp.getString("lat");
                String lang = tmp.getString("lng");

                result = new LatLng(Double.valueOf(lat), Double.valueOf(lang));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
