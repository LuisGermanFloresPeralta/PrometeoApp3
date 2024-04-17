package es.resisg.prometeoapp3.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.controlador.CuentasActivity.CuentasActivity;
import es.resisg.prometeoapp3.modelo.GestionSesion;
import es.resisg.prometeoapp3.modelo.conexionHTTP.peticiones;
import es.resisg.prometeoapp3.modelo.cuentasSQLite.cuentasSQLiteOpenHelper;

public class EditarCuentaActivity extends AppCompatActivity {


    EditText edtContrasena,edtNombre;
    TextView txtViewUsuario;
    Bundle b;
    int usuario_EXTAR;
    String contrasena_EXTRA,nombre_EXTAR;
    cuentasSQLiteOpenHelper myDB;
    GestionSesion gestionSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cuenta);

        //inicializar
        init();

    }
    public void init(){
        //conseguir sesion
        gestionSesion = new GestionSesion(this);

        //Relacionar los EditText de la parte gráfica con el código
        txtViewUsuario =(TextView) findViewById(R.id.txtViewUsuarioEditarCuenta);
        edtContrasena=(EditText) findViewById(R.id.edtContrasenaEditarCuenta);
        edtNombre=(EditText) findViewById(R.id.edtNombreEditarCuenta);

        //inicializar base de datos de cuentas
        myDB=new cuentasSQLiteOpenHelper(this);

        //obtener datos pasados desde Cuentas Activity para editar
        b=getIntent().getExtras();
        usuario_EXTAR = b.getInt("usuario_PUT_EXTRA");
        contrasena_EXTRA= b.getString("contrasena_PUT_EXTRA");
        nombre_EXTAR= b.getString("nombre_PUT_EXTRA");

        //Settear contraseña y nombre a los campos en la parte gráfica
        txtViewUsuario.setHint(String.valueOf(usuario_EXTAR));
        edtContrasena.setText(contrasena_EXTRA);
        edtNombre.setText(nombre_EXTAR);
    }

    //onclick para editarCuenta del boton:btnEditarEditarCuenta del layout:activity_editar_cuenta.xml
    public void editarCuenta(View view){
        String contrasena=edtContrasena.getText().toString().trim();
        String nombre =edtNombre.getText().toString().trim();
        //validamos los datos de sobre todo contrasena y usuario
        if(validarUsuarioContrasenaNombre(String.valueOf(usuario_EXTAR),contrasena,nombre)){
            //se comprueba que el usuario con esta nueva contraseña existe en la INTRANET de IES LAS SALINAS
            String respuesta =new peticiones("http://192.168.1.49/WEB/APP/appValidaUsuario.php",String.valueOf(usuario_EXTAR),contrasena).conseguirNombreUsuario();
            if(respuesta.equals("0")){
                Toast.makeText(this, "Usuario no registrado, hable con secretaria", Toast.LENGTH_SHORT).show();
            }else {
                /* si el numero identificador del usuario actual coincide con el del usuario pasado atravéz del intent
                la sesion actual se actualizará con la contraseña y nombre modificado */
                if(gestionSesion.getUsuario()==usuario_EXTAR){
                    gestionSesion.iniciarSesion(usuario_EXTAR,contrasena,nombre);
                }
                myDB.editarCuenta(usuario_EXTAR,contrasena,nombre);
                irCuentasActivity();
            }
        }
    }

    //onclick para volver del boton:btnVolverEditarCuenta del layout:activity_editar_cuenta.xml
    public void volver(View view){
        irCuentasActivity();
    }
    //onclick para cancelar del boton:btnCancelarEditarCuenta del layout:activity_editar_cuenta.xml
    public void cancelar(View view){
        irCuentasActivity();
    }

    //metodo para ir a CuentasActivity
    public void irCuentasActivity(){
        Intent i = new Intent(this, CuentasActivity.class);
        finish();
        startActivity(i);
    }


    public boolean validarUsuarioContrasenaNombre(String usuario,String contrasena,String nombre){
        if(usuario.isEmpty()&&contrasena.isEmpty()&&nombre.isEmpty()){
            Toast.makeText(this, "Tienes que rellenar todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }else if (usuario.isEmpty()){
            Toast.makeText(this, "El campo usuario esta vacío", Toast.LENGTH_SHORT).show();
            return false;
        }else if (contrasena.isEmpty()) {
            Toast.makeText(this, "El campo contraseña esta vacío", Toast.LENGTH_SHORT).show();
            return false;
        } else if (nombre.isEmpty()) {
            Toast.makeText(this, "El campo nombre esta vacío", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!(usuario.matches(".*\\d.*"))){
            Toast.makeText(this, "El campo usuario debe contener números", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }
}