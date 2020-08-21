package com.example.midtermproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mContactListView;
    private ArrayList<UserContact> mContactArrayList;
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

        loadData();
        initComponent();
    }

    private void loadData() {
        mContactArrayList = new ArrayList<>();
        UserContact userContact = new UserContact("Thanh Dat", "093534923", "khoa hoc tu nhien",R.drawable.logo_quan_con_lan);
        mContactArrayList.add(userContact);
    }

    private void initComponent() {
        mContactListView = (ListView) findViewById(R.id.contact_listview);

        mContactListViewAdapter = new ListViewAdapter(this,R.layout.listview_contact_item, mContactArrayList);
        mContactListView.setAdapter(mContactListViewAdapter);
        mContactListView.setOnItemClickListener(mListViewItemOnClick);


    }

    public void onClickAddButton(View view) {
    }
}