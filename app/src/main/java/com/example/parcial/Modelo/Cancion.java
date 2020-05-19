package com.example.parcial.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.parcial.Persistencia.PersistenciaEnMemoria;

import java.util.ArrayList;
import java.util.List;

public class Cancion {
    public int id;
    public String nombre;
    public String artista;


    public Cancion() {
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getArtista() {
        return artista;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public void guardarCancion(){
        PersistenciaEnMemoria.listaDeCanciones.add(this);
    }

    public Boolean existeCancion(){
        Boolean existeCancion = false;
        for (Cancion c: PersistenciaEnMemoria.listaDeCanciones ) {
            if(c.getNombre().equals(nombre)){
                existeCancion = true;
            };
        }
        return existeCancion;
    }
    public Boolean eliminarDeLaLista(Cancion cancion){
        int cont = 0;
        for (Cancion c: PersistenciaEnMemoria.listaDeCanciones ) {
            if(c.getNombre().equals(cancion.getNombre())){
                 PersistenciaEnMemoria.listaDeCanciones.remove(cont);
                 return true;
            }
            cont++;
        }
        return false;
    }


}
