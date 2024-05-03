package es.resisg.prometeoapp3.modelo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.controlador.ConectadoActivity.ConectadoActivity;
import es.resisg.prometeoapp3.controlador.MainActivity;
import es.resisg.prometeoapp3.modelo.conexionHTTP.peticiones;

public class MiServicio extends Service {
    private static final String CHANNEL_ID = "my_channel";
    private Handler handler;
    private Runnable runnable;
    private GestionSesion gestionSesion;
    private int NumeroNotificacionesAnteriores=0;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                //Recuperamos datos de la sesion(usuario y contraseña)
                gestionSesion = new GestionSesion(MiServicio.this);

                //Hacemos la peticion para recoger la cantidad de notificaciones que existen
                List<String> notificacionesList = new peticiones("http://192.168.1.141/WEB/APP/appNotificaciones.php", String.valueOf(gestionSesion.getUsuario()), gestionSesion.getContrasena()).conseguirNotificaciones();
                for (int i =0;i<notificacionesList.size();i++){
                    if(notificacionesList.size()>NumeroNotificacionesAnteriores){
                        createNotificationChannel();
                        showNotification(notificacionesList.get(i),i);
                    }
                }

                //añadimos la cantidad de notificaciones que existen en este momento
                NumeroNotificacionesAnteriores=notificacionesList.size();

                handler.postDelayed(this,60000);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Crear un canal de notificación para dispositivos Android Oreo y superiores
        /*createNotificationChannel();

        // Mostrar una notificación
        showNotification();*/

        handler.post(runnable);
        //No tocar
        return START_STICKY;
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My Channel";
            String description = "Channel for my app";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Registrar el canal en el sistema
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification(String notificacion, int i) {
        // Crear un Intent para abrir la actividad principal de tu aplicación
        Intent intent = new Intent(this, ConectadoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_usuario_morado)
                .setContentTitle(gestionSesion.getNombre()+" tiene nueva acticvidad")
                .setContentText("Ver detalles")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificacion))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent) // Establecer el PendingIntent
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(i, builder.build());
    }



    /* @Override
    public void onDestroy() {
        super.onDestroy();
        // Detiene la ejecución del Runnable cuando el servicio es destruido
        handler.removeCallbacks(runnable);
    }*/
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}