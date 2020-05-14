package com.example.parcial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.parcial.Controlador.ListarService;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ListarService serv =new ListarService(Main2Activity.this);
        serv.execute();
    }
}
