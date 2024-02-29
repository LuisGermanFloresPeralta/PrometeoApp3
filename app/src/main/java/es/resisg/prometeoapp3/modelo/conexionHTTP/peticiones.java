package es.resisg.prometeoapp3.modelo.conexionHTTP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import es.resisg.prometeoapp3.clases.ActuacionParticular;

public class peticiones {

    //atrtibutos
    String url;
    String usuario;
    String contrasena;

    //construtores
    public peticiones(String url, String usuario, String contrasena) {
        this.url = url;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    //metodos
    public ArrayList<ActuacionParticular> conseugirActuaciones () {

        ArrayList<ActuacionParticular> actuaciones;
        try {
            //instancio arrayList a devolver
            actuaciones = new ArrayList<>();
            //hacemos la conexion http y lo almacenamos en String respuesta
            String respuesta = new conexionHTTP().execute(url, usuario, contrasena).get();
            //lo convertimos a JSONArray
            JSONArray jsonArray = new JSONArray(respuesta);
            //Conseguimos cada elemento de jsonArray con JSONObject obtenemos cada atributo y rellenamos el ArrayList actuaciones
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //almacenamos los datos en cada una de las variables
                String nombre_profesor = jsonObject.getString("Nombre_profesor");
                Date fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonObject.getString("Fecha"));
                String tipoActuacion = jsonObject.getString("Tipo_actuacion");
                String comentario = jsonObject.getString("Comentario");
                actuaciones.add(new ActuacionParticular(nombre_profesor, fecha, tipoActuacion, comentario));

            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        //finalmente devolvemos el arrayList actuaciones
        return actuaciones;
    }

    public String conseguirNombreUsuario(){

        String respuesta;
        try {
            respuesta = new conexionHTTP().execute(url,usuario,contrasena).get().trim();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return respuesta;
    }
}
