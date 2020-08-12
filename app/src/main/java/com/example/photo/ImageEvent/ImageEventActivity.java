package com.example.photo.ImageEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.photo.R;
import static com.example.photo.MainActivity.al_images;


public class ImageEventActivity extends AppCompatActivity {
    int position;
    int int_position;
    ViewPager mViewPager;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_event);
        Intent intent = getIntent();
        int_position = intent.getIntExtra("int_position",0);
        position = intent.getIntExtra("position",0);
//        getFragment(PhotoFragment.newInstance(int_position,position));
        frameLayout = findViewById(R.id.container);
        final ImageView imageView = findViewById(R.id.im);
        mViewPager = findViewById(R.id.vp);
        AdapterFragment adapterFragment = new AdapterFragment(getBaseContext(),getSupportFragmentManager(),al_images.get(int_position).getCount(),int_position);
        mViewPager.setAdapter(adapterFragment);
        mViewPager.setCurrentItem(position);
    }
}