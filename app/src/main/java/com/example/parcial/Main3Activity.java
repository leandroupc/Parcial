package com.example.parcial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parcial.Controlador.BuscarCancionService;
import com.example.parcial.Modelo.Cancion;
import com.example.parcial.Persistencia.PersistenciaEnMemoria;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Main3Activity extends AppCompatActivity {

    public static Activity activity;
    public static EditText txt_nombre, txt_artista;
    private Button btnBuscar;
    private EditText txt_buscar;
    private FloatingActionButton btnGuadar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        activity=this;
        txt_nombre = (EditText) findViewById(R.id.txtNombreCancion);
        txt_artista = (EditText) findViewById(R.id.txtArtista);
        txt_buscar = (EditText) findViewById(R.id.txtBuscar);
        btnBuscar = (Button) findViewById(R.id.btn_buscar);
        btnGuadar = (FloatingActionButton) findViewById(R.id.btnGuardar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarCancionService servicio = new BuscarCancionService(Main3Activity.this,txt_buscar.getText().toString());
                servicio.execute();
            }
        });

        btnGuadar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });





    }

    public void guardar(){
        if(txt_nombre.getText().equals("") || txt_artista.getText().equals("")){
            Toast.makeText(activity, "Aun no ha buscado una cancion", Toast.LENGTH_SHORT).show();
        }else{
            Cancion c = new Cancion();
            c.setNombre(txt_nombre.getText().toString());
            c.setArtista(txt_artista.getText().toString());
            if(c.existeCancion()){
                Toast.makeText(activity, "La cancion ya existe en la PlayList", Toast.LENGTH_SHORT).show();
            }else{
                c.guardarCancion();
                Toast.makeText(activity, "Cancion guardada", Toast.LENGTH_SHORT).show();
                Intent activity = new Intent(this,MainActivity.class);
                startActivity(activity);

            }



        }
    }

}
