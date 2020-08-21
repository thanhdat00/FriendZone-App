package com.example.midtermproj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<UserContact> {
    private Context mContext;
    private int mLayoutId;
    private ArrayList<UserContact> mContactList;

    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List<UserContact> objects) {
        super(context, resource, objects);
        mContext = context;
        mLayoutId = resource;
        mContactList = (ArrayList<UserContact>) objects;
    }

    @Override
    public int getCount() { return mContactList.size(); }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(mLayoutId, null, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.friend_name);
            viewHolder.info=(TextView) convertView.findViewById(R.id.friend_information);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        UserContact userContact = mContactList.get(position);
        viewHolder.name.setText(userContact.getName());
        viewHolder.info.setText(userContact.getPhoneNumber());


        return convertView;
    }

    static class ViewHolder{
        TextView name;
        TextView info;


    }
}
