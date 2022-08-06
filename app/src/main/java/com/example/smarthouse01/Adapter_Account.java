package com.example.smarthouse01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_Account extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<User> listUser;

    public Adapter_Account(Context context, int layout, ArrayList<User> listUser) {
        this.context = context;
        this.layout = layout;
        this.listUser = listUser;
    }

    @Override
    public int getCount() {
        return listUser.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);

        TextView txtView = view.findViewById(R.id.TWname);

        listUser.get(i);

        txtView.setText(listUser.get(i).getEmail());

        return view;
    }
}
