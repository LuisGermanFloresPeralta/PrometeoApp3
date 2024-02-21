package es.resisg.prometeoapp3.controlador.ConectadoActivity;

import static es.resisg.prometeoapp3.controlador.MainActivity.SHARED_PREF_NAME;
import static es.resisg.prometeoapp3.controlador.MainActivity.KEY_USUARIO;
import static es.resisg.prometeoapp3.controlador.MainActivity.KEY_CONTRASENA;
import static es.resisg.prometeoapp3.controlador.MainActivity.KEY_NOMBRE;
import static es.resisg.prometeoapp3.controlador.MainActivity.sharedPreferencesSesionActual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.controlador.MainActivity;
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
        /*b=getIntent().getExtras();
        usuario_pasado=b.getString("usuario");
        contrasena_pasada=b.getString("contrasena");*/
        //
        usuario_pasado="1244884";
        contrasena_pasada="1244884";


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

        //instanciamos el bundel para pasar datos(usuario y contraseña) a los fragment
        Bundle bundle = new Bundle();
        bundle.putString("usuario", usuario);
        bundle.putString("contrasena", contrasena);

        // añadimos el Bundle al siguiente fragmento
        fragment.setArguments(bundle);

        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
    //accion para boton Salir
    public void cerrarSesion(View view){
        SharedPreferences.Editor editor = sharedPreferencesSesionActual.edit();
        editor.putString(KEY_USUARIO,"");
        editor.putString(KEY_CONTRASENA,"");
        editor.commit();
        Intent intent = new Intent(this,MainActivity.class);
        finish();
        startActivity(intent);

    }
}