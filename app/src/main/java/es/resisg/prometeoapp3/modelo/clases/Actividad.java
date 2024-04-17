package es.resisg.prometeoapp3.modelo.clases;

import java.io.Serializable;

public class Actividad implements Serializable {

    //atributos
    String Nombre_Profesor,Fecha,Actividad,Nota;

    //constructor
    public Actividad(String nombre_Profesor, String fecha, String actividad, String nota) {
        Nombre_Profesor = nombre_Profesor;
        Fecha = fecha;
        Actividad = actividad;
        Nota = nota;
    }

    //getterss
    public String getNombre_Profesor() {
        return Nombre_Profesor;
    }

    public String getFecha() {
        return Fecha;
    }

    public String getActividad() {
        return Actividad;
    }

    public String getNota() {
        return Nota;
    }
}
