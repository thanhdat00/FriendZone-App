package com.example.midtermproj;

import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<UserContact> {
    private Context mContext;
    private int mLayoutId;
    private ArrayList<UserContact> mContactList;
    private Bitmap bmp;

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
            viewHolder.photo= (de.hdodenhof.circleimageview.CircleImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);

        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final UserContact userContact = mContactList.get(position);
        viewHolder.name.setText(userContact.getName());
        viewHolder.info.setText(userContact.getPhoneNumber());

        if(userContact.getPhotoID()!=null) {
            Picasso.get().load(userContact.getPhotoID()).into(viewHolder.photo);
        }
        else {
            viewHolder.photo.setImageResource(R.drawable.ic_baseline_person_pin_circle_24);
        }
        //call button
        ImageButton callButton = (ImageButton)  convertView.findViewById(R.id.PhoneCall);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri number = Uri.parse("tel:" + userContact.getPhoneNumber());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                mContext.startActivity(callIntent);
        }
        });
        return convertView;
    }

    static class ViewHolder{
        TextView name;
        TextView info;
        de.hdodenhof.circleimageview.CircleImageView photo;

    }
}
