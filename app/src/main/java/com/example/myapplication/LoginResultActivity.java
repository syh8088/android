package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class LoginResultActivity extends AppCompatActivity {

    TextView textView_get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_get = findViewById(R.id.TextView_get);

        Intent intent = getIntent();
        Bundle bundle =  intent.getExtras();
        String email = bundle.getString("email");
        String password = bundle.getString("password");

        textView_get.setText(email + " / " + password);

    }
}
