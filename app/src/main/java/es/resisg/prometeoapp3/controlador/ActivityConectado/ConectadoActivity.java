package es.resisg.prometeoapp3.controlador.ActivityConectado;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.databinding.ActivityConectadoBinding;
import es.resisg.prometeoapp3.controlador.ActivityConectado.fragmetos.ActuacionesParticularesFragment;
import es.resisg.prometeoapp3.controlador.ActivityConectado.fragmetos.FaltasFragment;
import es.resisg.prometeoapp3.controlador.ActivityConectado.fragmetos.NotasFragment;

public class ConectadoActivity extends AppCompatActivity {

    ActivityConectadoBinding binding;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConectadoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //primero cargamos el primer fragmento por defecto
        loadFragment(new ActuacionesParticularesFragment());

        //agregamos un selectedListener al BottonNavigation
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            int itemId = item.getItemId();
            if(itemId== R.id.itemActuacionesBottonNavigation){
                loadFragment(new ActuacionesParticularesFragment());
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
    // este metodo se encarga de cambiar o cargar el fragmento
    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}