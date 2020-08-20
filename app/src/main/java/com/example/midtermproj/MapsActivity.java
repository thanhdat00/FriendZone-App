package com.example.midtermproj;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.Manifest;
import android.content.Intent;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LoaderManager.LoaderCallbacks<LatLng>  {

    private GoogleMap mMap;
    private Marker mMarker;
    private UserContact mUserContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        loadData();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        enableMyLocation();
        displayMarker();
    }

    private void displayMarker() {

        Bundle queryBundle = new Bundle();
        queryBundle.putString("queryString", mUserContact.getAddress());
        getSupportLoaderManager().restartLoader(0,queryBundle, this);
    }

    private void loadData() {
        Intent intent = getIntent();

        mUserContact = new UserContact(intent.getStringExtra("name"), intent.getStringExtra("phonenumber")
                , intent.getStringExtra("address"));
    }

    // Get the current Location
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }  else {
            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                    1);
        }
    }

    @NonNull
    @Override
    public Loader<LatLng> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";

        if (args != null) {
            queryString = args.getString("queryString");
        }

        return new PlacesLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<LatLng> loader, LatLng data) {
        if (data != null)
        {
            mMarker = mMap.addMarker(new MarkerOptions().position(data)
                                                        .title(mUserContact.getName()));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15),2000,null);
            CameraPosition cameraPosition = new CameraPosition.Builder().target(data).zoom(15).bearing(90).tilt(30).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<LatLng> loader) {

    }
}