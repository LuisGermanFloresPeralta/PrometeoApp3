package es.resisg.prometeoapp3.controlador.ConectadoActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.controlador.CuentasActivity.CuentasActivity;
import es.resisg.prometeoapp3.databinding.ActivityConectadoBinding;
import es.resisg.prometeoapp3.controlador.ConectadoActivity.fragmetos.ActuacionesFragment;
import es.resisg.prometeoapp3.controlador.ConectadoActivity.fragmetos.FaltasFragment;
import es.resisg.prometeoapp3.controlador.ConectadoActivity.fragmetos.NotasFragment;

public class ConectadoActivity extends AppCompatActivity {


    ActivityConectadoBinding binding;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConectadoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //primero cargamos el primer fragmento por defecto
        loadFragment(new ActuacionesFragment());

        //agregamos un selectedListener al BottonNavigation
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            int itemId = item.getItemId();
            if(itemId== R.id.itemActuacionesBottonNavigation){
                loadFragment(new ActuacionesFragment());
                return true;
            }else if(itemId==R.id.itemNotasBottonNavigation){
                loadFragment(new NotasFragment());
                return true;
            } else if (itemId==R.id.itemFaltasBottonNavigation) {
                loadFragment(new FaltasFragment());
                return true;
            }

            return false;
        });

    }
    // este metodo se encarga de cambiar o cargar el fragmento, tambien pasamos el usuario y comtrasena
    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
    //accion para abrir activityCuentas
    public void irACuentas(View view){
        Intent i = new Intent(this, CuentasActivity.class);
        finish();
        startActivity(i);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}