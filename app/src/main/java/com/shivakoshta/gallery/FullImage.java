package com.shivakoshta.gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.nio.file.Files;
import java.util.ArrayList;

public class FullImage extends AppCompatActivity {
    ArrayList list ;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        imageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        list = (ArrayList) bundle.getParcelableArrayList("list");
        int position = intent.getIntExtra("position",0);
        String name = intent.getStringExtra("name");
        imageView.setImageURI(Uri.parse(list.get(position).toString()));


    }
}