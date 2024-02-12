package es.resisg.prometeoapp3.modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import es.resisg.prometeoapp3.modelo.conexion.Conectar;

public class ActuacionParticular {
    //attributes
    String Nombre_profesor;
    Date Fecha;
    String Tipo_actuacion;
    String Comentario;

    //constructors
    public ActuacionParticular(String nombre_profesor, Date fecha, String tipo_actuacion, String comentario) {
        Nombre_profesor = nombre_profesor;
        Fecha = fecha;
        Tipo_actuacion = tipo_actuacion;
        Comentario = comentario;
    }
    public ActuacionParticular() {
    }

    //getters
    public String getNombre_profesor() {
        return Nombre_profesor;
    }

    public Date getFecha() {
        return Fecha;
    }

    public String getTipo_actuacion() {
        return Tipo_actuacion;
    }

    public String getComentario() {
        return Comentario;
    }

}
