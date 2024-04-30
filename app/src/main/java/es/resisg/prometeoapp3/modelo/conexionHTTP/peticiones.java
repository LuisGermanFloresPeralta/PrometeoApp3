package es.resisg.prometeoapp3.modelo.conexionHTTP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import es.resisg.prometeoapp3.modelo.clases.Actividad;
import es.resisg.prometeoapp3.modelo.clases.ActuacionParticular;
import es.resisg.prometeoapp3.modelo.clases.Asignatura;
import es.resisg.prometeoapp3.modelo.clases.Evaluacion;
import es.resisg.prometeoapp3.modelo.clases.Falta;

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
    public ArrayList<ActuacionParticular> conseguirActuaciones () {

        ArrayList<ActuacionParticular> actuaciones= new ArrayList<>();
        try {
            String respuesta = new conexionHTTP().execute(url, usuario, contrasena).get();//hacemos la conexion http y lo almacenamos en String respuesta
            JSONArray jsonArray = new JSONArray(respuesta);//lo convertimos a JSONArray
            for (int i = 0; i < jsonArray.length(); i++) {//Conseguimos cada elemento de jsonArray con JSONObject obtenemos cada atributo y rellenamos el ArrayList actuaciones
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
        if(actuaciones.isEmpty()){
            actuaciones.add(new ActuacionParticular("No tienes ninguna Actuacion"));
            return actuaciones;
        }else {
            return actuaciones;
        }
    }
    public ArrayList<Evaluacion> conseguirEvaluaciones () {
        ArrayList<Evaluacion> evaluaciones= new ArrayList<>();// Se inicializa un ArrayList para almacenar las evaluaciones obtenidas
        try {
            String respuesta = new conexionHTTP().execute(url, usuario, contrasena).get();//hacemos la conexion http y lo almacenamos en String respuesta
            JSONArray jsonArrayEvaluacines = new JSONArray(respuesta);// Se convierte la respuesta en un JSONArray
            for (int x = 0; x < jsonArrayEvaluacines.length(); x++) {// Iteramos sobre cada elemento del jsonArrayEvaluacines para obtener cada evaluación

                JSONObject jsonObjectEvaluacion = jsonArrayEvaluacines.getJSONObject(x);// Obtenemos el jsonObjectEvaluacion de la evaluación actual
                String evaluacion = jsonObjectEvaluacion.getString("Evaluacion");// Extraemos el nombre de la evaluación
                JSONArray jsonArrayAsignaturas = jsonObjectEvaluacion.getJSONArray("Asignaturas");// Extraemos el JSONArray de asignaturas asociadas a esta evaluación

                ArrayList<Asignatura> asignaturas= new ArrayList<>();// Inicializamos un ArrayList<Asignatura> para almacenar las asignaturas de esta evaluación
                for(int y=0;y<jsonArrayAsignaturas.length();y++){// Iteramos sobre cada asignatura en el JSONArray

                    JSONObject jsonObjectAsignatura = jsonArrayAsignaturas.getJSONObject(y);// Obtenemos el JSONObject de la asignatura actual
                    String asignatura = jsonObjectAsignatura.getString("Asignatura");// Extraemos el nombre de la asignatura
                    JSONArray jsonArrayActividades = jsonObjectAsignatura.getJSONArray("Actividades");// Extraemos el JSONArray de actividades asociadas a esta asignatura

                    ArrayList<Actividad> actividades= new ArrayList<>();// Inicializamos un ArrayList para almacenar las actividades de esta asignatura
                    for(int z=0;z<jsonArrayActividades.length();z++){// Iteramos sobre cada actividad en el JSONArray

                        JSONObject jsonObjectActividad = jsonArrayActividades.getJSONObject(z);// Obtenemos el JSONObject de la actividad actual
                        // Extraemos los datos de la actividad
                        String nombre_profesor = jsonObjectActividad.getString("Nombre_Profesor");
                        String fecha =jsonObjectActividad.getString("Fecha");
                        String actividad=jsonObjectActividad.getString("Actividad");
                        String nota=jsonObjectActividad.getString("Nota");

                        // Creamos un objeto Actividad con los datos extraídos y lo añadimos al ArrayList de actividades
                        actividades.add(new Actividad(nombre_profesor,fecha,actividad,nota));
                    }
                    // Creamos un objeto Asignatura con el nombre y la lista de actividades y lo añadimos al ArrayList de asignaturas
                    asignaturas.add(new Asignatura(asignatura,actividades));
                }
                // Creamos un objeto Evaluacion con el nombre y la lista de asignaturas y lo añadimos al ArrayList de evaluaciones
                evaluaciones.add(new Evaluacion(evaluacion,asignaturas));//anadimos la evaluacion con las asignaturas junto con las actividades
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Devolvemos el ArrayList de evaluaciones
        if(evaluaciones.isEmpty()){
            evaluaciones.add(new Evaluacion("No tienes ninguna Evaluacion"));
            return evaluaciones;
        }else {
            return evaluaciones;
        }
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

    public List<String> conseguirNotificaciones(){
        List<String> notificaciones = new ArrayList<>();
        try {
            String respuesta = new conexionHTTP().execute(url,usuario,contrasena).get().trim();
            JSONArray jsonArrayNotificaciones = new JSONArray(respuesta);

            for (int i=0;i<jsonArrayNotificaciones.length();i++){
                String notificacion = jsonArrayNotificaciones.getString(i);
                notificaciones.add(notificacion);
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return notificaciones;
    }

    public ArrayList<Falta> conseguirFaltas(){
        //Instanciamos un ArrayList de faltas
        ArrayList<Falta> faltasArrayList = new ArrayList<>();

        try {
            //hacemos la peticion al servidor y almacenamos la respuesta en una variable String
            String respuesta = new conexionHTTP().execute(url,usuario,contrasena).get().trim();
            JSONArray jsonArrayFaltas = new JSONArray(respuesta);

            for (int i=0;i<jsonArrayFaltas.length();i++){//Recorremos el JSONArray de faltas

                JSONObject jsonObjectFalta = jsonArrayFaltas.getJSONObject(i);//tomamos el objeto JSONObject y asignamos cada valor a las variables String
                String diaSemanaFecha = jsonObjectFalta.getString("Dia_semana_y_Fecha");
                String tipoFalta= jsonObjectFalta.getString("Tipo_falta");
                String horaFalta=jsonObjectFalta.getString("Hora_falta");
                faltasArrayList.add(new Falta(diaSemanaFecha,tipoFalta,horaFalta));// se anade el nuevo objeto Falta al array de faltas
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        //Devolvemos el ArrayList
        if(faltasArrayList.isEmpty()){
            faltasArrayList.add(new Falta("No tienes ninguna Falta"));
            return faltasArrayList;
        }else {
            return faltasArrayList;
        }
    }

}
