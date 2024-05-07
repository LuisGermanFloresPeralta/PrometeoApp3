package es.resisg.prometeoapp3.modelo.conexionHTTP;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class conexionHTTP extends AsyncTask<String,String,String> {

    private Context context;

    public conexionHTTP(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {

        // Verificar la conexión a Internet antes de continuar
        if (!isInternetAvailable()) {
            Toast.makeText(context, "No tienes conexion a internet", Toast.LENGTH_SHORT).show();
            return null; // No hay conexión a Internet, retorna null
        }

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

            //Devolverla respuesta con los caractéres quitados
            return quitarCaracteresNoDeseados(respuesta.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
    public static String quitarCaracteresNoDeseados (String respuesta) {

        // Especifica los símbolos que deseas quitar
        String pattern1 = "\uFEFF\uFEFF\uFEFF";
        String pattern2 = "\uFEFF\uFEFF";
        String pattern3 = "\uFEFF";


        // Aplica la expresión regular para reemplazar los símbolos con una cadena vacía
        String result = respuesta.replaceAll(pattern1, "");
        result =result.replaceAll(pattern2, "");
        result =result.replaceAll(pattern3, "");

        return result;
    }
    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}
