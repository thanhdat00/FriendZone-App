package com.example.midtermproj;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TASKS = "dataArrayList";
    private static final int REQ_CODE = 0;

    private ListView mContactListView;
    private ArrayList<UserContact> mContactArrayList;
    private ListViewAdapter mContactListViewAdapter;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private ListView.OnItemClickListener mListViewItemOnClick = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);

            UserContact userContact = mContactArrayList.get(position);

            intent.putExtra("name", userContact.getName());
            intent.putExtra("address", userContact.getAddress());
            intent.putExtra("phonenumber", userContact.getPhoneNumber());
            intent.putExtra("photoid", userContact.getPhotoID());

            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermission()) {
                requestForSpecificPermission();
            }
        }

        loadData();
        initComponent();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return  true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.map:
                //code xử lý khi bấm menu1
                Toast.makeText(this, "nothing", Toast.LENGTH_SHORT).show();
                break;

            case R.id.addFriend:
                intentToAddFriend();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void intentToAddFriend() {
        Intent intent= new Intent(MainActivity.this, AddFriendActivity.class);
        intent.putExtra("contactlist", mContactArrayList);
        startActivityForResult(intent, REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_CODE && data != null) {
                mContactArrayList =
                        (ArrayList<UserContact>) data.getSerializableExtra("listcontactback");
                InitListView();
            }
        }
    }

    private void loadData() {
        mContactArrayList = new ArrayList<>();
        if (getArrayList() != null) mContactArrayList = getArrayList();
    }

    private void initComponent() {

        // init share preference
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        // init list view
        InitListView();
    }

    private void InitListView() {
        mContactListView = (ListView) findViewById(R.id.contact_listview);
        mContactListViewAdapter = new ListViewAdapter(this,R.layout.listview_contact_item, mContactArrayList);
        mContactListView.setAdapter(mContactListViewAdapter);
        mContactListView.setOnItemClickListener(mListViewItemOnClick);
    }

    // get arrray list
    public ArrayList<UserContact> getArrayList(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = prefs.getString(TASKS, null);
        Type type = new TypeToken<ArrayList<UserContact>>() {}.getType();
        return gson.fromJson(json, type);
    }

    // save array list using sharedPreferences
    public void saveArrayList(ArrayList<UserContact> list){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(TASKS, json);
        editor.commit();     // This line is IMPORTANT !!!
    }

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

                // other 'case' lines to check for other
                // permissions this app might request
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveArrayList(mContactArrayList);
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveArrayList(mContactArrayList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveArrayList(mContactArrayList);
    }
}