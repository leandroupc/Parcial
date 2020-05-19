package com.example.parcial.Controlador;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.parcial.Main3Activity;
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

public class BuscarCancionService extends AsyncTask<Void,Void,String> {
    Context context;
    ProgressDialog ventana;
    String nombreCancion;
    Cancion cancionEncontrada = new Cancion();

    public BuscarCancionService(Context context, String nombreCancion) {
        this.context = context;
        this.nombreCancion = nombreCancion;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        ventana = ProgressDialog.show(this.context,"Canciones","Buscando cancion...");
    }

    protected String doInBackground(Void... voids) {

        String uri = "http://ws.audioscrobbler.com/2.0/?method=track.search&track="+nombreCancion+"&api_key=b284db959637031077380e7e2c6f2775&format=json";
        URL url = null;
        try {
            url = new URL(uri);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setReadTimeout(30000);
            urlConnection.setConnectTimeout(30000);
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
                JSONArray resultado_busqueda = json_response.getJSONObject("results").getJSONObject("trackmatches").getJSONArray("track");
                JSONObject primerResultado = null;
                if(resultado_busqueda.length() > 0){
                    primerResultado = resultado_busqueda.getJSONObject(0);

                    cancionEncontrada.setNombre(primerResultado.getString("name"));
                    cancionEncontrada.setArtista(primerResultado.getString("artist"));
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
        if (respuesta.equals("sin errores")){
            Main3Activity.activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Main3Activity.txt_nombre.setText(cancionEncontrada.getNombre());
                    Main3Activity.txt_artista.setText(cancionEncontrada.getArtista());
                }
            });

        }else{
            Toast.makeText(context, "No se obtuvieron resultados en la busqueda", Toast.LENGTH_SHORT).show();


        }
    }
}
