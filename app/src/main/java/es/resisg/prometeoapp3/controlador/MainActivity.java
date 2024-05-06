package es.resisg.prometeoapp3.controlador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.controlador.Adapters.notificacionesAdapter;
import es.resisg.prometeoapp3.modelo.ServicioManager;
import es.resisg.prometeoapp3.modelo.clases.Cuenta;
import es.resisg.prometeoapp3.controlador.ConectadoActivity.ConectadoActivity;
import es.resisg.prometeoapp3.modelo.GestionSesion;
import es.resisg.prometeoapp3.modelo.conexionHTTP.peticiones;
import es.resisg.prometeoapp3.modelo.cuentasSQLite.cuentasSQLiteOpenHelper;

public class MainActivity extends AppCompatActivity {
    //Atributos
    EditText edtUsuario,edtContrasena;
    private GestionSesion gestionSesion;
    SharedPreferences notificacionSegundoPlano;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        permisoNotificaccion();
    }
    public void init(){
        //inicializamos el SharedPreferences
        gestionSesion= new GestionSesion(this);
        //relacionar atributos con la parte gráfica
        edtUsuario =(EditText) findViewById(R.id.edtUsuario_main_layout);
        edtContrasena =(EditText) findViewById(R.id.edtContrasena_main_layout);
        //validamos la sesion y que comprobar que no se ha cambiado la contrasena en otra plataforma.
        String respuesta =new peticiones("http://ieslassalinas.org/APP/appValidaUsuario.php",String.valueOf(gestionSesion.getUsuario()),gestionSesion.getContrasena()).conseguirNombreUsuario();
        if (gestionSesion.validarSesion()) {
            if(respuesta.equals("0")){
                Toast.makeText(this, "Se han cambiado las credenciales desde otro dispositivo", Toast.LENGTH_SHORT).show();
            }else {
                irActivityConectado();
            }
        }
    }

    //metodo de acceso
    public void Aceder(View view){
        String usuario=edtUsuario.getText().toString();
        String contrasena=edtContrasena.getText().toString();
        //pasamos usuario,contrasena al metodo validarUsuarioContrasena
        if(validarUsuarioContrasena(usuario,contrasena)){
            String respuesta =new peticiones("http://ieslassalinas.org/APP/appValidaUsuario.php",usuario,contrasena).conseguirNombreUsuario();
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
        //con el servicio manager activamos el servicio de notificaciones
        ServicioManager.getInstance(getApplicationContext()).iniciarServicio();

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

    public void permisoNotificaccion (){

        notificacionSegundoPlano= getSharedPreferences("notificacionYactividadSengundoPlano", this.MODE_PRIVATE);

        boolean Accede = notificacionSegundoPlano.getBoolean("accedeAirAPermisos", false); // false es el valor por defecto si la clave no existe

        if(Accede==false){
            //Relacionamos el recycler view con la parte gráfica de la aplicacion
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_permitir_notificaciones, null);
            builder.setView(dialogView);

            Dialog dialog = builder.create();
            //aplicar fondo al Dialog
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.fondo_rv_actuaciones);

            //configuracion de boton cancelar del dialogo
            dialogView.findViewById(R.id.btnCancelar_dialog_permitir_notificaciones).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Cerrar el diálogo
                    dialog.dismiss();
                }
            });

            //configuracion del boton ir a permisos del boton Ir a permisos
            dialogView.findViewById(R.id.btnIrPermisos_dialog_permitir_notificaciones).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //-------------------------------------------------------------------
                    //guardamos que si se ha accedido a Permisos de las aplicaciones
                    SharedPreferences.Editor editor = notificacionSegundoPlano.edit();// Utiliza SharedPreferences.Editor para editar los valores
                    // Guarda el valor booleano
                    boolean valorBooleano = true; // por ejemplo, tu valor booleano
                    editor.putBoolean("accedeAirAPermisos", valorBooleano);
                    editor.apply();// Aplica los cambios

                    //-------------Vamos a la gestion de permisos----------------
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_SETTINGS);
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                    startActivity(intent);
                }
            });

            // finalmente mostrar el dialogo
            dialog.show();
        }

    }
}