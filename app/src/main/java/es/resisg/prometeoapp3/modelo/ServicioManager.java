package es.resisg.prometeoapp3.modelo;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

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

    public void iniciarServicio() {
        Intent servicioIntent = new Intent(context, MiServicio.class);
        context.startService(servicioIntent);
    }
}
