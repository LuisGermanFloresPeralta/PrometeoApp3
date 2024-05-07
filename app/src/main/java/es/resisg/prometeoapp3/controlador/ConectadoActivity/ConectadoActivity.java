package es.resisg.prometeoapp3.controlador.ConectadoActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.controlador.Adapters.notificacionesAdapter;
import es.resisg.prometeoapp3.controlador.CuentasActivity.CuentasActivity;
import es.resisg.prometeoapp3.databinding.ActivityConectadoBinding;
import es.resisg.prometeoapp3.controlador.ConectadoActivity.fragmetos.ActuacionesFragment;
import es.resisg.prometeoapp3.controlador.ConectadoActivity.fragmetos.FaltasFragment;
import es.resisg.prometeoapp3.controlador.ConectadoActivity.fragmetos.NotasFragment;
import es.resisg.prometeoapp3.modelo.GestionSesion;
import es.resisg.prometeoapp3.modelo.ServicioManager;
import es.resisg.prometeoapp3.modelo.conexionHTTP.peticiones;

public class ConectadoActivity extends AppCompatActivity {


    ActivityConectadoBinding binding;
    private RecyclerView recyclerViewNotificaciones;
    private notificacionesAdapter notificacionesAdapter;
    private GestionSesion gestionSesion;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConectadoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Recuperamos datos de la sesion(usuario y contraseña)
        gestionSesion = new GestionSesion(ConectadoActivity.this);

        configurarDialog();
        //primero cargamos el primer fragmento por defecto
        loadFragment(new ActuacionesFragment());

        //agregamos un selectedListener al BottonNavigation
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            int itemId = item.getItemId();
            if (itemId == R.id.itemActuacionesBottonNavigation) {
                loadFragment(new ActuacionesFragment());
                return true;
            } else if (itemId == R.id.itemNotasBottonNavigation) {
                loadFragment(new NotasFragment());
                return true;
            } else if (itemId == R.id.itemFaltasBottonNavigation) {
                loadFragment(new FaltasFragment());
                return true;
            }

            return false;
        });

    }

    // este metodo se encarga de cambiar o cargar el fragmento, tambien pasamos el usuario y comtrasena
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    //accion para abrir activityCuentas
    public void irACuentas(View view) {
        Intent i = new Intent(this, CuentasActivity.class);
        finish();
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();
        configurarDialog();
    }

    public void configurarDialog(){
        //instanciamos la lista de notificaciones que existe
        List<String> notificacionesList = new ArrayList<>();

        //revisamos si disponemos de alguna notificacion
        notificacionesList = new peticiones("http://ieslassalinas.org/APP/appRevisaDatos2.php", String.valueOf(gestionSesion.getUsuario()), gestionSesion.getContrasena(),ConectadoActivity.this).conseguirNotificaciones();

        if(notificacionesList.size()!=0){
            showDialog(notificacionesList);
        }
    }



    private void showDialog(List<String> notificacionesList) {

        //Relacionamos el recycler view con la parte gráfica de la aplicacion
        AlertDialog.Builder builder = new AlertDialog.Builder(ConectadoActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_notification_layout, null);
        builder.setView(dialogView);


        recyclerViewNotificaciones = dialogView.findViewById(R.id.recyclerViewNuevasNotificaciones);
        notificacionesAdapter = new notificacionesAdapter(notificacionesList);
        recyclerViewNotificaciones.setAdapter(notificacionesAdapter);
        recyclerViewNotificaciones.setLayoutManager(new LinearLayoutManager(ConectadoActivity.this));

        Dialog dialog = builder.create();
        //aplicar fondo al Dialog
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.fondo_rv_actuaciones);

        //configuracion de boton cancelar del dialogo
        dialogView.findViewById(R.id.imgViewCerrarDialog_notification_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cerrar el diálogo
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}