package com.example.parcial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.parcial.Controlador.ListarService;
import com.example.parcial.Persistencia.PersistenciaEnMemoria;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    public static RecyclerView recyclerView;
    private ReciclerPlaylist adapter;
    public static Activity activity;
    private FloatingActionButton btnNuevaCancion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity=this;
        recyclerView = (RecyclerView) findViewById(R.id.recicler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnNuevaCancion = (FloatingActionButton) findViewById(R.id.floatingActionButton3) ;
        PersistenciaEnMemoria.iniciarPersistencia();
        ListarService servicio = new ListarService(this);
        servicio.execute();
        btnNuevaCancion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity = new Intent(MainActivity.this, Main3Activity.class);
                startActivity(activity);
            }
        });

    }
}
