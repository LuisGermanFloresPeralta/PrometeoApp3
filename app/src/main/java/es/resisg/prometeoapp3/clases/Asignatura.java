package es.resisg.prometeoapp3.clases;

import java.util.ArrayList;

public class Asignatura {

    //Atributos
    String Asignatura;
    ArrayList<Actividad> Actividades;
    boolean expandible;

    //Constructor
    public Asignatura(String asignatura, ArrayList<Actividad> actividades) {
        Asignatura = asignatura;
        Actividades = actividades;
        expandible = false;
    }

    //Getters
    public String getAsignatura() {
        return Asignatura;
    }

    public ArrayList<Actividad> getActividades() {
        return Actividades;
    }

    public boolean isExpandible() {
        return expandible;
    }

    //setters
    public void setExpandible(boolean expandible) {
        this.expandible = expandible;
    }
}
