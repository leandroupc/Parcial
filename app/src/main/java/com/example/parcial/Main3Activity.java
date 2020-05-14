package com.example.parcial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class Main3Activity extends AppCompatActivity {

    public static Activity activity;
    public static EditText txt_nombre, txt_artista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

    }
}
