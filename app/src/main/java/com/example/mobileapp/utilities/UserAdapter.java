package com.example.mobileapp.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mobileapp.models.User;
import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    private int resourceId;

    public UserAdapter(Context context, int textViewResourceId, List<User> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.userAvatar = (ImageView) view.findViewById(com.example.mobileapp.R.id.friend_image);
            viewHolder.userName = (TextView) view.findViewById(com.example.mobileapp.R.id.friend_name);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.userAvatar.setImageResource(user.getAvatar());
        viewHolder.userName.setText(user.getName());
        return view;
    }

    class ViewHolder {
        ImageView userAvatar;
        TextView userName;
    }
}

