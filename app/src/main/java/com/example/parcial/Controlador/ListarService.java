package com.example.parcial.Controlador;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.parcial.MainActivity;
import com.example.parcial.Modelo.PlayList;
import com.example.parcial.Routes.Routes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ListarService extends AsyncTask<Void,Void,String> {
    Context context;
    ProgressDialog progressDialog;
    Boolean error;

    public ListarService(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(this.context,"Canciones","Validando");
    }

    protected String doInBackground(Void... voids) {

        String uri = Routes.listar;
        URL url = null;
        try {
            url = new URL(uri);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            int response_code = urlConnection.getResponseCode();
            if (response_code == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String linea = "";
                while ((linea = br.readLine()) != null){
                    sb.append(linea);
                    break;
                }
                br.close();

                String jo = "";
                jo = sb.toString();

                JSONObject json = null;
                json = new JSONObject(jo);

                JSONObject json_cancion = null;
                json_cancion = new JSONObject(jo);
                json_cancion = json.getJSONObject("tracks");

                JSONArray array_canciones = json_cancion.getJSONArray("track");

                for (int i=0; i<array_canciones.length();i++){

                    JSONObject jsonPlaylist = array_canciones.getJSONObject(i);

                    PlayList playlist = new PlayList();

                    playlist.nombre = jsonPlaylist.getString("name");
                    JSONObject artista = jsonPlaylist.getJSONObject("artist");
                    playlist.artista = artista.getString("name");
                    playlist.Save(this.context);
                }
                this.error = false;
                return "ok";
            }

        } catch (MalformedURLException e) {
            error = true;
            return e.getMessage();
        } catch (IOException e) {
            error = true;
            return e.getMessage();
        } catch (JSONException e) {
            error = true;
            return e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    protected void onPostExecute(String respuesta) {
        super.onPostExecute(respuesta);
        progressDialog.dismiss();
        if (error){
            Toast.makeText(context, "No se pudieron cargar las canciones", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
    }
}
