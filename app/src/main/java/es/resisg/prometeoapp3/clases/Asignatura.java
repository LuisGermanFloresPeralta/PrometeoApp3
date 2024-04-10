package es.resisg.prometeoapp3.clases;

import java.util.ArrayList;

public class Asignatura {

    //Atributos
    String Asignatura;
    ArrayList<Actividad> Actividades;

    //Constructor
    public Asignatura(String asignatura, ArrayList<Actividad> actividades) {
        Asignatura = asignatura;
        Actividades = actividades;
    }

    //Getters
    public String getAsignatura() {
        return Asignatura;
    }

    public ArrayList<Actividad> getActividades() {
        return Actividades;
    }
}
