package com.example.parcial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.parcial.Controlador.ListarService;
import com.example.parcial.Modelo.PlayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ReciclerPlaylist adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recicler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ReciclerPlaylist(PlayList.FindAll(this), this);
        recyclerView.setAdapter(adapter);
    }
}
