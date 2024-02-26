package es.resisg.prometeoapp3.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import es.resisg.prometeoapp3.R;

public class AnadirCuentaActivity extends AppCompatActivity {

    EditText edtNombre,edtUsuario,edtContrasena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_cuenta);

        edtNombre = (EditText) findViewById(R.id.edtNombreAnadirCuenta);
        edtUsuario=(EditText) findViewById(R.id.edtUsuarioAnadirCuenta);
        edtContrasena=(EditText) findViewById(R.id.edtContrasenaAnadirCuenta);
    }

    public void Anadir(View view){

    }
}