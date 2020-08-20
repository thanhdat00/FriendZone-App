package com.example.midtermproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
        initComponent();
    }

    private void loadData() {
        mContactArrayList = new ArrayList<>();
        UserContact userContact = new UserContact("Thanh Dat", "093534923", "166 ly thai to");
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