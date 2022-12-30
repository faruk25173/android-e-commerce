package com.naogaon.papas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    TextView textview1,textview2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        textview2=(TextView) findViewById(R.id.textView2);
        textview1=(TextView) findViewById(R.id.textView1);
    }
}