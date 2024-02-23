package es.resisg.prometeoapp3.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.controlador.ConectadoActivity.ConectadoActivity;
import es.resisg.prometeoapp3.modelo.GestionSesion;
import es.resisg.prometeoapp3.modelo.conexionHTTP.peticiones;

public class MainActivity extends AppCompatActivity {
    //Atributos
    EditText edtUsuario,edtContrasena;
    private GestionSesion gestionSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    public void init(){
        //relacionar atributos con la parte gráfica
        edtUsuario =(EditText) findViewById(R.id.edtUsuario_main_layout);
        edtContrasena =(EditText) findViewById(R.id.edtContrasena_main_layout);
        //inicializamos el SharedPreferences
        gestionSesion= new GestionSesion(this);
        //validamos la sesion
        if (gestionSesion.validarSesion()) {
            irActivityConectado();
        }
    }

    //metodo de acceso
    public void Aceder(View view){
        String usuario = edtUsuario.getText().toString();
        String contrasena = edtContrasena.getText().toString();
        String respuesta="";
        if(usuario.isEmpty()&&contrasena.isEmpty()){
            Toast.makeText(this, "Tienes que rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }else if (usuario.isEmpty()){
            Toast.makeText(this, "El campo usuario esta vacío", Toast.LENGTH_SHORT).show();
        } else if (contrasena.isEmpty()) {
            Toast.makeText(this, "El campo contraseña esta vacío", Toast.LENGTH_SHORT).show();
        }else {
            respuesta=new peticiones("http://www.ieslassalinas.org/APP/appValidaUsuario.php",usuario,contrasena).conseguirNombreUsuario();
            if(respuesta.equals("\uFEFF\uFEFF\uFEFF0")){
                Toast.makeText(this, "Usuario no registrado, hable con secretaria", Toast.LENGTH_SHORT).show();
            }else {
                //guardamos los datos de usario y contraseña en el sharedPreferences
                gestionSesion.iniciarSesion(usuario,contrasena,respuesta);
                //Iniciamos el ActivityConectado
                irActivityConectado();
            }
        }
    }

    public void irActivityConectado(){
        Intent i = new Intent(MainActivity.this, ConectadoActivity.class);
        finish();
        startActivity(i);
    }
}