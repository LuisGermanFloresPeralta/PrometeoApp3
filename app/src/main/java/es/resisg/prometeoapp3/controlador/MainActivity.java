package es.resisg.prometeoapp3.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.clases.Cuenta;
import es.resisg.prometeoapp3.controlador.ConectadoActivity.ConectadoActivity;
import es.resisg.prometeoapp3.modelo.GestionSesion;
import es.resisg.prometeoapp3.modelo.conexionHTTP.peticiones;
import es.resisg.prometeoapp3.modelo.cuentasSQLite.cuentasSQLiteOpenHelper;

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
        //inicializamos el SharedPreferences
        gestionSesion= new GestionSesion(this);
        //relacionar atributos con la parte gráfica
        edtUsuario =(EditText) findViewById(R.id.edtUsuario_main_layout);
        edtContrasena =(EditText) findViewById(R.id.edtContrasena_main_layout);
        //validamos la sesion
        if (gestionSesion.validarSesion()) {
            irActivityConectado();
        }
    }

    //metodo de acceso
    public void Aceder(View view){
        String usuario=edtUsuario.getText().toString();
        String contrasena=edtContrasena.getText().toString();
        //pasamos usuario,contrasena al metodo validarUsuarioContrasena
        if(validarUsuarioContrasena(usuario,contrasena)){
            String respuesta =new peticiones("http://www.ieslassalinas.org/APP/appValidaUsuario.php",usuario,contrasena).conseguirNombreUsuario().replaceAll("[^a-zA-Z0-9áéíóúüÁÉÍÓÚÜ]", "");
            if(respuesta.equals("0")){
                Toast.makeText(this, "Usuario no registrado, hable con secretaria", Toast.LENGTH_SHORT).show();
                borrarCampos();
            }else {
                int INTusuario = Integer.valueOf(usuario.replaceAll("[^0-9]",""));
                //guardamos la cuenta en la base de datos interna de Cuentas
                guardarCuenta(INTusuario,contrasena,respuesta);
                //guardamos los datos de usario y contraseña en el sharedPreferences
                gestionSesion.iniciarSesion(INTusuario,contrasena,respuesta);
                //Iniciamos el ActivityConectado
                irActivityConectado();
            }
        }else {
            borrarCampos();
        }

    }
    public void borrarCampos(){
        edtUsuario.setText("");
        edtContrasena.setText("");
    }
    public boolean validarUsuarioContrasena(String usuario,String contrasena){
        if(usuario.isEmpty()&&contrasena.isEmpty()){
            Toast.makeText(this, "Tienes que rellenar todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }else if (usuario.isEmpty()){
            Toast.makeText(this, "El campo usuario esta vacío", Toast.LENGTH_SHORT).show();
            return false;
        }else if (contrasena.isEmpty()) {
            Toast.makeText(this, "El campo contraseña esta vacío", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!(usuario.matches(".*\\d.*"))){
            Toast.makeText(this, "El campo usuario debe contener números", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }
    

    public void irActivityConectado(){
        Intent i = new Intent(MainActivity.this, ConectadoActivity.class);
        finish();
        startActivity(i);
    }
    public void guardarCuenta(int usuario, String contrasena, String nombre){
        cuentasSQLiteOpenHelper myDB = new cuentasSQLiteOpenHelper(MainActivity.this);
        ArrayList<Cuenta> cuentas = myDB.obtenerTodasLasCuentas();
        if (cuentas.size() != 0) {
            boolean cuentaExistente = false;

            for (Cuenta c : cuentas) {
                if (c.getUsuario()==(usuario)) {
                    Toast.makeText(this, "El usuario anteriormente registrado en la app", Toast.LENGTH_SHORT).show();
                    cuentaExistente = true;
                    gestionSesion.iniciarSesion(c.getUsuario(), c.getContrasena(), c.getNombre());
                    irActivityConectado();
                    break;
                }
            }

            if (!cuentaExistente) {
                myDB.anadirCuenta(usuario, contrasena, nombre);
            }
        } else {
            myDB.anadirCuenta(usuario, contrasena, nombre);
            irActivityConectado();
        }

    }
}