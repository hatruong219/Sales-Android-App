package com.sict.shopdt.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sict.shopdt.R;

public class Aboutme extends AppCompatActivity {
    ImageView aboutme;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_aboutme );
        getSupportActionBar().hide();
        aboutme = (ImageView) findViewById( R.id.imgMe );
        Glide.with( getApplication() )
                .load( R.drawable.aboutme ).centerCrop().optionalCircleCrop()
                .into( aboutme );
    }
}