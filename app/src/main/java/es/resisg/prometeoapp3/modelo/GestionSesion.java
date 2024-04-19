package es.resisg.prometeoapp3.modelo;

import android.content.Context;
import android.content.SharedPreferences;

public class GestionSesion {

    //Atributos
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public static final String SHARED_PREF_NAME = "shpSesion";
    public static final String KEY_USUARIO = "shpUsuario";
    public static final String KEY_CONTRASENA = "shpContrasena";
    public static final String KEY_NOMBRE = "shpNombre";

    //constructor-----------------------------------------------------------------
    public GestionSesion(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //Getters---------------------------------------------------------------------
    public int getUsuario() {
        return sharedPreferences.getInt(KEY_USUARIO, 0);
    }

    public String getContrasena() {
        return sharedPreferences.getString(KEY_CONTRASENA, "");
    }

    public String getNombre() {
        return sharedPreferences.getString(KEY_NOMBRE, "");
    }

    //metodos---------------------------------------------------------------------------------------------
    public void iniciarSesion(int usuario, String contrasena, String nombre) {
        editor.putInt(KEY_USUARIO, usuario);
        editor.putString(KEY_CONTRASENA, contrasena);
        editor.putString(KEY_NOMBRE, nombre);
        editor.apply();
    }

    public boolean validarSesion() {
        return !(sharedPreferences.getInt(KEY_USUARIO, 0)==0) && !sharedPreferences.getString(KEY_CONTRASENA, "").isEmpty();
    }

    public void cerrarSesion() {
        editor.clear().apply();
    }
}
