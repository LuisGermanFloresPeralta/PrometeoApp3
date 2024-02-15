package es.resisg.prometeoapp3.controlador.ConectadoActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.databinding.ActivityConectadoBinding;
import es.resisg.prometeoapp3.controlador.ConectadoActivity.fragmetos.ActuacionesFragment;
import es.resisg.prometeoapp3.controlador.ConectadoActivity.fragmetos.FaltasFragment;
import es.resisg.prometeoapp3.controlador.ConectadoActivity.fragmetos.NotasFragment;

public class ConectadoActivity extends AppCompatActivity {

    ActivityConectadoBinding binding;

    Bundle b;
    String usuario_pasado,contrasena_pasada;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConectadoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //usuario y contraseña resivido por bundel desde el MainActivity
        b=getIntent().getExtras();
        usuario_pasado=b.getString("usuario");
        contrasena_pasada=b.getString("contrasena");

        //primero cargamos el primer fragmento por defecto
        loadFragment(new ActuacionesFragment(),usuario_pasado,contrasena_pasada);

        //agregamos un selectedListener al BottonNavigation
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            int itemId = item.getItemId();
            if(itemId== R.id.itemActuacionesBottonNavigation){
                loadFragment(new ActuacionesFragment(),usuario_pasado,contrasena_pasada);
                return true;
            }else if(itemId==R.id.itemNotasBottonNavigation){
                loadFragment(new NotasFragment(),usuario_pasado,contrasena_pasada);
                return true;
            } else if (itemId==R.id.itemFaltasBottonNavigation) {
                loadFragment(new FaltasFragment(),usuario_pasado,contrasena_pasada);
                return true;
            }

            return false;
        });
    }
    // este metodo se encarga de cambiar o cargar el fragmento, tambien pasamos el usuario y comtrasena
    private void loadFragment(Fragment fragment,String usuario, String contrasena){

        //instanciamos el bundel para pasar datos a los fragment
        Bundle bundle = new Bundle();
        bundle.putString("usuario", usuario);
        bundle.putString("contrasena", contrasena);

        // Establecer el Bundle en el fragmento
        fragment.setArguments(bundle);

        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}