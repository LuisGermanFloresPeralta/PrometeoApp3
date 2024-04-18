package es.resisg.prometeoapp3.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.controlador.CuentasActivity.CuentasActivity;
import es.resisg.prometeoapp3.modelo.conexionHTTP.peticiones;
import es.resisg.prometeoapp3.modelo.cuentasSQLite.cuentasSQLiteOpenHelper;

public class AnadirCuentaActivity extends AppCompatActivity {

    EditText edtUsuario,edtContrasena,edtNombre;
    CheckBox chBoxNombreDefecto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_cuenta);

        edtUsuario=(EditText) findViewById(R.id.edtUsuarioAnadirCuenta);
        edtContrasena=(EditText) findViewById(R.id.edtContrasenaAnadirCuenta);
        edtNombre = (EditText) findViewById(R.id.edtNombreAnadirCuenta);
        chBoxNombreDefecto =(CheckBox) findViewById(R.id.checkBoxAnadirCuenta);
    }

    public void Anadir(View view){
        String usuario = edtUsuario.getText().toString();
        String contrasena = edtContrasena.getText().toString().trim();
        String nombre = edtNombre.getText().toString().trim();
        if(validarUsuarioContrasenaNombre(usuario,contrasena,nombre)){
            String respuesta =new peticiones("http://ieslassalinas.org/APP/appValidaUsuario.php",usuario,contrasena).conseguirNombreUsuario();
            if(respuesta.equals("0")){
                Toast.makeText(this, "Usuario no registrado, hable con secretaria", Toast.LENGTH_SHORT).show();
            }else {
                cuentasSQLiteOpenHelper myDB = new cuentasSQLiteOpenHelper(AnadirCuentaActivity.this);
                if(chBoxNombreDefecto.isChecked()==true){
                    nombre=respuesta;
                }
                myDB.anadirCuenta(Integer.valueOf(usuario.replaceAll("[^0-9]","")),contrasena,nombre);
                cancelarVolver(view);
            }
        }

    }
    public void cancelarVolver(View view){
        Intent i = new Intent(this, CuentasActivity.class);
        finish();
        startActivity(i);
    }
    public boolean validarUsuarioContrasenaNombre(String usuario,String contrasena,String nombre){
        if(usuario.isEmpty()&&contrasena.isEmpty()&&nombre.isEmpty()){
            Toast.makeText(this, "Tienes que rellenar todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }else if (usuario.isEmpty()){
            Toast.makeText(this, "usuario esta vacío", Toast.LENGTH_SHORT).show();
            return false;
        }else if (contrasena.isEmpty()) {
            Toast.makeText(this, "contraseña esta vacío", Toast.LENGTH_SHORT).show();
            return false;
        } else if (nombre.isEmpty() && chBoxNombreDefecto.isChecked()==false){
            Toast.makeText(this, "nombre esta vacío O marque NombreDefecto", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!(usuario.matches(".*\\d.*"))){
            Toast.makeText(this, "El usuario debe contener números", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }
}