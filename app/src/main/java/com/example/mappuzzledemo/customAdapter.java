package com.example.mappuzzledemo;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class customAdapter extends BaseAdapter {
    private ArrayList<Button> mButtons;
    private int mColumnWidth, mColumnHeight;

    public customAdapter (ArrayList<Button>mButtons, int columnWidth, int coloumnHeight)
    {
        this.mButtons = mButtons;
        mColumnWidth = columnWidth;
        mColumnHeight = coloumnHeight;
    }
    @Override
    public int getCount() {
        return mButtons.size();
    }

    @Override
    public Object getItem(int position) {
        return (Object) mButtons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;

        if (convertView == null) button = mButtons.get(position);
        else button = (Button) convertView;

        AbsListView.LayoutParams param = new AbsListView.LayoutParams(mColumnWidth, mColumnHeight);

        button.setLayoutParams(param);

        return button;
    }
}
