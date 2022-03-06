package com.example.roadsidevehicleasistance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    Button button;
    ImageView srch;
    Button profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        srch = findViewById(R.id.imageView);


        button = (Button) findViewById(R.id.button11);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button11:
                        startActivity(new Intent(HomePage.this, Searchdata.class));
                        break;

                }
            }
        });
        profile=(Button) findViewById(R.id.button12);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, Profile.class);
                startActivity(intent);
            }
        });

    }
}
