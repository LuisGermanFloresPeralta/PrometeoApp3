package es.resisg.prometeoapp3.modelo.servicioNotificaciones;

import android.content.Context;
import android.content.Intent;

public class ServicioManager {
    private static ServicioManager instance;
    private Context context;

    private ServicioManager(Context context) {
        this.context = context.getApplicationContext();
    }

    public static synchronized ServicioManager getInstance(Context context) {
        if (instance == null) {
            instance = new ServicioManager(context);
        }
        return instance;
    }

    // Métodos adicionales de gestión del servicio aquí

    public void iniciarServicio() {
        Intent servicioIntent = new Intent(context, Servicio.class);
        context.startService(servicioIntent);
    }

    public void detenerServicio() {
        Intent servicioIntent = new Intent(context, Servicio.class);
        context.stopService(servicioIntent);
    }
}

