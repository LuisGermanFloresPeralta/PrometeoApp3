package es.resisg.prometeoapp2.conexion;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Conectar extends AsyncTask<String,String,String> {


    @Override
    protected String doInBackground(String... strings) {

        String URL = strings[0];
        String usuario= strings[1];
        String contrasena=strings[2];


        HttpURLConnection httpURLConnection=null;
        URL url =null;
        try {
            url = new URL(URL);
            httpURLConnection  =(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");

            // Agregue los parámetros necesarios a la solicitud
            String urlParameters = "usuario="+usuario+"&contrasena="+contrasena;
            httpURLConnection.setDoOutput(true);
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(urlParameters.getBytes());
            os.flush();
            os.close();


            httpURLConnection.connect();
            int codigo = httpURLConnection.getResponseCode();
            if(codigo == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String lineas="";
                StringBuffer buffer = new StringBuffer();
                while ((lineas=reader.readLine()) != null){
                    buffer.append(lineas);
                }
                return buffer.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
