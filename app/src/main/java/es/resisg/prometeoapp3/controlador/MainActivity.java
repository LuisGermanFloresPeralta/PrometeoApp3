package es.resisg.prometeoapp3.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.controlador.ConectadoActivity.ConectadoActivity;
import es.resisg.prometeoapp3.modelo.conexion.Conectar;
import es.resisg.prometeoapp3.modelo.conexion.datos;

public class MainActivity extends AppCompatActivity {
    //Atributos
    EditText edtUsuario,edtContrasena;
    public static SharedPreferences sharedPreferencesSesionActual;
    SharedPreferences sharedPreferencesSesiones;
    public static final String SHARED_PREF_NAME="shpSesion";
    public static final String KEY_USUARIO="shpUsuario";
    public static final String KEY_CONTRASENA="shpContrasena";
    public static final String KEY_NOMBRE="shpNombre";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        validarSesion();


    }
    public void init(){
        //inicializamos el SharedPreferences
        sharedPreferencesSesionActual = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        //relacionar atributos con la parte gráfica
        edtUsuario =(EditText) findViewById(R.id.edtUsuario_main_layout);
        edtContrasena =(EditText) findViewById(R.id.edtContrasena_main_layout);
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
            respuesta=new datos("http://www.ieslassalinas.org/APP/appValidaUsuario.php",usuario,contrasena).nombreUsuario();
            if(respuesta.equals("\uFEFF\uFEFF\uFEFF0")){
                Toast.makeText(this, "Usuario no registrado, hable con secretaria", Toast.LENGTH_SHORT).show();
            }else {
                //guardamos los datos de usario y contraseña en el sharedPreferences
                iniciarSesion(usuario,contrasena,respuesta);
                //Iniciamos el ActivityConectado
                irActivityConectado();
            }
        }



    }
    public void validarSesion(){
        if(sharedPreferencesSesionActual.getString(KEY_USUARIO,"")!=""&&sharedPreferencesSesionActual.getString(KEY_CONTRASENA,"")!=""){
            irActivityConectado();
        }
    }

    public void iniciarSesion(String usuario,String contrasena,String nombre){
        SharedPreferences.Editor editor = sharedPreferencesSesionActual.edit();
        editor.putString(KEY_USUARIO,usuario);
        editor.putString(KEY_CONTRASENA,contrasena);
        editor.putString(KEY_NOMBRE,nombre);
        editor.commit();
    }

    public void irActivityConectado(){
        Intent i = new Intent(MainActivity.this, ConectadoActivity.class);
        finish();
        startActivity(i);
    }
}