package com.example.midtermproj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mContactListView;
    private ArrayList<UserContact> mContactArrayList=new ArrayList<>();
    private ListViewAdapter mContactListViewAdapter;


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

                Toast.makeText(this, "Activate Zoom Mode", Toast.LENGTH_SHORT).show();

                break;
            case R.id.addFriend:
                //code xử lý khi bấm menu2
                Intent intent= new Intent(MainActivity.this, AddFriendActivity.class);
                Toast.makeText(this, "Refresh Images", Toast.LENGTH_SHORT).show();

                startActivity(intent);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


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


            for(int i = 0;i<15;++i) {
                loadData();
            }
            initComponent();

    }

    private void loadData() {


        String al = getIntent().getStringExtra("Name");
        mContactArrayList.add(new UserContact(getIntent().getStringExtra("Name"),
                getIntent().getStringExtra("Phone"), getIntent().getStringExtra("Address"),R.drawable.logo_quan_con_lan));

    }

    private void initComponent() {
        mContactListView = (ListView) findViewById(R.id.contact_listview);

        mContactListViewAdapter = new ListViewAdapter(this,R.layout.listview_contact_item, mContactArrayList);
        mContactListView.setAdapter(mContactListViewAdapter);
        mContactListView.setOnItemClickListener(mListViewItemOnClick);


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


    public void onClickAddButton(View view) {
    }
}