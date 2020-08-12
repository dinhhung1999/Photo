package com.example.photo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import com.example.photo.ImageEvent.ImageEventActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.photo.MainActivity.al_images;

public class PhotosActivity extends AppCompatActivity {
    int int_position;
    private GridView gridView;
    public GridViewAdapter adapter;
//    List<Model_images> images = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        int_position = getIntent().getIntExtra("value", 0);
//        images = (Model_images) getIntent().getSerializableExtra("al_images");
        gridView = (GridView)findViewById(R.id.gv_folder);
        adapter = new GridViewAdapter(this, al_images,int_position);
        adapter.setiItemImageOnClick(new iItemImageOnClick() {
            @Override
            public void iItemImageOnClick(Model_images image, int position) {
                Intent intent = new Intent(getBaseContext(), ImageEventActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("int_position",int_position);
                startActivity(intent);
            }
        });
        gridView.setAdapter(adapter);
    }
}