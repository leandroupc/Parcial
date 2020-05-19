package com.example.parcial.Persistencia;

import com.example.parcial.Modelo.Cancion;

import java.util.ArrayList;

public class PersistenciaEnMemoria {
    public static ArrayList<Cancion> listaDeCanciones = null;

    public static void iniciarPersistencia(){
        if(listaDeCanciones==null){
            listaDeCanciones = new ArrayList<>();
        }
    }
}
