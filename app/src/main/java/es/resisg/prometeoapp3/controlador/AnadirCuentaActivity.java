package es.resisg.prometeoapp3.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.controlador.CuentasActivity.CuentasActivity;
import es.resisg.prometeoapp3.modelo.cuentasSQLite.cuentasSQLiteOpenHelper;

public class AnadirCuentaActivity extends AppCompatActivity {

    EditText edtUsuario,edtContrasena,edtNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_cuenta);

        edtUsuario=(EditText) findViewById(R.id.edtUsuarioAnadirCuenta);
        edtContrasena=(EditText) findViewById(R.id.edtContrasenaAnadirCuenta);
        edtNombre = (EditText) findViewById(R.id.edtNombreAnadirCuenta);
    }

    public void Anadir(View view){
        String usuario = edtUsuario.getText().toString().trim();
        String contrasena = edtContrasena.getText().toString().trim();
        String nombre = edtNombre.getText().toString().trim();
        cuentasSQLiteOpenHelper myDB = new cuentasSQLiteOpenHelper(AnadirCuentaActivity.this);
        myDB.anadirCuenta(usuario,contrasena,nombre);
    }
    public void cancelarVolver(View view){
        Intent i = new Intent(this, CuentasActivity.class);
        finish();
        startActivity(i);
    }
}