package com.shivakoshta.gallery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    GridView image_gridView;
    ArrayList<File> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R ) {
//            list = fetchImage(Environment.getStorageDirectory());
//        }

        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                list = fetchImage(Environment.getExternalStorageDirectory());

                image_gridView = findViewById(R.id.image_grid);
                image_gridView.setAdapter(new GridAdapter());

                image_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this,FullImage.class);
                    intent.putExtra("name", list.get(position).getName());
                    intent.putExtra("list",list);
                    intent.putExtra("position",position);
                    startActivity(intent);

                    }
                });
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }
    public class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View ConvertView = null;
            if(ConvertView==null)
            {
                ConvertView = getLayoutInflater().inflate(R.layout.row_layout,parent,false);
                ImageView imageView = ConvertView.findViewById(R.id.layout_image);
                Uri ImageUri = Uri.parse(list.get(position).toString());
                imageView.setImageURI(ImageUri);
            }
            return ConvertView;
        }
    }

    private ArrayList<File> fetchImage(File MainDirectory)
    {
        ArrayList<File> Images = new ArrayList<>();
        File[] list = MainDirectory.listFiles();
        if(list==null)
            return Images;

        for (File file : list) {
            if (file.isDirectory()) {
                Images.addAll(fetchImage(file));
            }
            else {
                if (!file.isHidden()) {
                    if (file.getName().endsWith(".jpg")) {
                        Images.add(file);
                    }
                }
            }
        }
        return Images;
    }

}