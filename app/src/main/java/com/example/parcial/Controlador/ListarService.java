package com.example.parcial.Controlador;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.parcial.MainActivity;
import com.example.parcial.Modelo.Cancion;
import com.example.parcial.Persistencia.PersistenciaEnMemoria;
import com.example.parcial.ReciclerPlaylist;

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
    ProgressDialog ventana;

    public ListarService(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        ventana = ProgressDialog.show(this.context,"Canciones","Validando...");
    }

    protected String doInBackground(Void... voids) {

        String uri = "http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=b284db959637031077380e7e2c6f2775&format=json";
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

                JSONObject json_response = new JSONObject(sb.toString());
                JSONArray json_playList = json_response.getJSONObject("tracks").getJSONArray("track");

                for (int i=0; i<json_playList.length();i++){

                    JSONObject response = json_playList.getJSONObject(i);

                    Cancion cancion = new Cancion();

                    cancion.nombre = response.getString("name");
                    cancion.artista = response.getJSONObject("artist").getString("name");
                    if(cancion.existeCancion()){
                       cancion.eliminarDeLaLista(cancion);
                    }
                    cancion.guardarCancion();
                }
                return "sin errores";
            }

        } catch (MalformedURLException e) {
            return e.getMessage();
        } catch (IOException e) {
            return e.getMessage();
        } catch (JSONException e) {
            return e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    protected void onPostExecute(String respuesta) {
        super.onPostExecute(respuesta);
        ventana.dismiss();
        if (respuesta.equals("sin errores") == false){
            Toast.makeText(context, "No se pudieron cargar las canciones", Toast.LENGTH_SHORT).show();
        }else{
            MainActivity.activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ReciclerPlaylist adapter = new ReciclerPlaylist(PersistenciaEnMemoria.listaDeCanciones, context);
                    MainActivity.recyclerView.setAdapter(adapter);
                }
            });

        }
    }
}
