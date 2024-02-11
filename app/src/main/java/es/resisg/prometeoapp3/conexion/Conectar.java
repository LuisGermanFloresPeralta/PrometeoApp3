package es.resisg.prometeoapp3.conexion;

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

        //se recogen las variables pasadas como argumentos
        String URL = strings[0];
        String usuario= strings[1];
        String contrasena=strings[2];


        try {
            //Se instancia la URL y se crea la conexion HttpURELConecciton por el metodo POST
            URL url = new URL(URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");

            // Agregue los parámetros necesarios a la solicitud
            String parametrosURL = "usuario="+usuario+"&contrasena="+contrasena;
            httpURLConnection.setDoOutput(true);
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(parametrosURL.getBytes());
            os.flush();
            os.close();

            // Obtener la respuesta del servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer respuesta = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                respuesta.append(inputLine);
            }
            in.close();

            //Devolverla respuesta
            return respuesta.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
