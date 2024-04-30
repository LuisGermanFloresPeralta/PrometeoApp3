package es.resisg.prometeoapp3.modelo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import es.resisg.prometeoapp3.R;

public class MiServicio extends Service {
    private static final String CHANNEL_ID = "my_channel";
    private Handler handler;
    private Runnable runnable;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MiServicio.this, "hola", Toast.LENGTH_SHORT).show();
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

    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_addusuario)
                .setContentTitle("Mi notificación")
                .setContentText("Hola, esta es una notificación de prueba!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
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