package com.example.photo.ImageEvent;

import android.content.Context;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AdapterFragment extends FragmentPagerAdapter {
    private Context mycontext;
    int totalTabs;
    int int_position;
    public AdapterFragment(Context context, FragmentManager fm, int totalTabs,int int_position){
        super(fm);
        mycontext = context;
        this.totalTabs = totalTabs;
        this.int_position = int_position;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        PhotoFragment photoFragment = null;
        try {
            photoFragment = new PhotoFragment(int_position,position);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photoFragment;
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}
