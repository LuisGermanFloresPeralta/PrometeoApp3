package es.resisg.prometeoapp3.modelo.clases;

import java.util.ArrayList;

public class Evaluacion {

    //Atributos
    private String Evaluacion;
    private ArrayList<Asignatura> Asignaturas;
    private boolean expandible;

    //Constructor
    public Evaluacion(String evaluacion, ArrayList<Asignatura> asignaturas) {
        Evaluacion = evaluacion;
        Asignaturas = asignaturas;
        expandible = false;
    }

    public Evaluacion(String evaluacion) {
        Evaluacion = evaluacion;
        Asignaturas = new ArrayList<>();
        expandible = false;
    }

    //Getters
    public String getEvaluacion() {
        return Evaluacion;
    }

    public ArrayList<Asignatura> getAsignaturas() {
        return Asignaturas;
    }

    public boolean isExpandible() {
        return expandible;
    }

    //Setters
    public void setExpandible(boolean expandible) {
        this.expandible = expandible;
    }
}
