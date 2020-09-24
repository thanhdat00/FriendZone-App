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
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class MapsAllFriend extends FragmentActivity implements OnMapReadyCallback, LoaderManager.LoaderCallbacks<LatLng> {

    private GoogleMap mMap;
    private enum PolylineStyle {
        DOTTED,
        PLAIN
    }

    private static final int REQ_PERMISSION = 1 ;

    private Marker mMarker;
    private ArrayList<UserContact> userContactArrayList;
    private UserContact mUserContact;

    private LatLng mUserGeo;
    private Bitmap bmp=null;
    private TextToSpeech mText2Speech;
    private boolean mIsText2SpeechReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_all_friend);
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

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //enableMyLocation();
        if(checkPermission())
            mMap.setMyLocationEnabled(true);
        else askPermission();
        displayMarker();

        LatLng location = new LatLng(14.058324, 108.277199);  // location of VietNam
        mMap.animateCamera(CameraUpdateFactory.zoomTo(5),10,null);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(6).bearing(90).tilt(30).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void displayMarker() {

        for (int i=0; i<userContactArrayList.size(); i++) {
            Bundle queryBundle = new Bundle();
            mUserContact = userContactArrayList.get(i);
            queryBundle.putString("queryString", mUserContact.getAddress());
            getSupportLoaderManager().restartLoader(i, queryBundle, this);
        }
    }

    private void loadData() {
        Intent intent = getIntent();

        userContactArrayList = (ArrayList<UserContact>) intent.getSerializableExtra("contactlist");
    }

    private void askPermission() {

        ActivityCompat.requestPermissions(
                this,
                new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                REQ_PERMISSION
        );
    }

    private boolean checkPermission() {

        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch ( requestCode ) {
            case REQ_PERMISSION: {
                if ( grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                    // Permission granted
                    if(checkPermission())
                        mMap.setMyLocationEnabled(true);

                } else {
                    // Permission denied
                }
                break;
            }
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
            if (mUserContact.getPhotoID()!= null) {

                Picasso.get().load(mUserContact.getPhotoID())
                        .into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                bmp = bitmap;
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        });

                bmp = Bitmap.createScaledBitmap(bmp, bmp.getWidth()/8, bmp.getHeight()/8, false);
                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bmp);
                mUserGeo = data;
                mMarker = mMap.addMarker(new MarkerOptions().position(data)
                        .title(mUserContact.getName())
                        .snippet(mUserContact.mAddress)
                        .icon(bitmapDescriptor) );
            }
            else
            {
                bmp = BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_person_round);
                bmp = Bitmap.createScaledBitmap(bmp, bmp.getWidth()/8, bmp.getHeight()/8, false);
                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bmp);
                mUserGeo = data;
                mMarker = mMap.addMarker(new MarkerOptions().position(data)
                        .title(mUserContact.getName())
                        .snippet(mUserContact.mAddress)
                        .icon(bitmapDescriptor));
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<LatLng> loader) {

    }
}