package com.example.parcial.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.parcial.BD;

import java.util.ArrayList;
import java.util.List;

public class PlayList {
    public int id;
    public String nombre;
    public String artista;


    public PlayList() {
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

    public void Save(Context context){
        BD bd = new BD(context, "p", null, 1);
        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("nombre", this.nombre);
        registro.put("artista", this.artista);
        db.insert("Playlist", null, registro);
        db.close();
    }
    public PlayList Find(Context context, int id){
        BD bd = new BD(context, "p", null, 1);
        SQLiteDatabase db = bd.getWritableDatabase();

        String sql = "select * from Playlist where id="+id;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            this.id = Integer.parseInt(cursor.getString(0));
            this.nombre = cursor.getString(1);
            this.artista = cursor.getString(2);
            return this;
        }
        db.close();
        return null;
    }
    public static List<PlayList> FindAll(Context context){
        BD bd = new BD(context, "p", null, 1);
        SQLiteDatabase db = bd.getWritableDatabase();
        List<PlayList> lista = new ArrayList<>();

        String sql = "select * from Playlist";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            do{
                PlayList play = new PlayList();
                play.id = Integer.parseInt(cursor.getString(0));
                play.nombre = cursor.getString(1);
                play.artista = cursor.getString(2);
                lista.add(play);
            }while(cursor.moveToNext());
            db.close();
            return lista;
        }
        db.close();
        return null;
    }
}
