package es.resisg.prometeoapp3.modelo.clases;

public class Falta {
    //Atributos
    String Dia_semana_y_Fecha,Tipo_falta,Hora_falta;

    //Constructor
    public Falta(String dia_semana_y_Fecha, String tipo_falta, String hora_falta) {
        Dia_semana_y_Fecha = dia_semana_y_Fecha;
        Tipo_falta = tipo_falta;
        Hora_falta = hora_falta;
    }

    //Getters
    public String getDia_semana_y_Fecha() {
        return Dia_semana_y_Fecha;
    }

    public String getTipo_falta() {
        return Tipo_falta;
    }

    public String getHora_falta() {
        return Hora_falta;
    }
}
