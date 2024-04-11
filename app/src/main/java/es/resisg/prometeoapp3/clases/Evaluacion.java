package es.resisg.prometeoapp3.clases;

import java.util.ArrayList;

public class Evaluacion {

    //Atributos
    String Evaluacion;
    ArrayList<Asignatura> Asignaturas;

    //Constructor
    public Evaluacion(String evaluacion, ArrayList<Asignatura> asignaturas) {
        Evaluacion = evaluacion;
        Asignaturas = asignaturas;
    }

    public Evaluacion(String evaluacion) {
        Evaluacion = evaluacion;
        Asignaturas = new ArrayList<>();
    }

    //Getters
    public String getEvaluacion() {
        return Evaluacion;
    }

    public ArrayList<Asignatura> getAsignaturas() {
        return Asignaturas;
    }
}
