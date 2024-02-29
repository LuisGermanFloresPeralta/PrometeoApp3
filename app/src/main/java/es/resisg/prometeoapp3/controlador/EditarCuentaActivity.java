package es.resisg.prometeoapp3.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.clases.Cuenta;
import es.resisg.prometeoapp3.controlador.ConectadoActivity.ConectadoActivity;
import es.resisg.prometeoapp3.controlador.CuentasActivity.CuentasActivity;
import es.resisg.prometeoapp3.modelo.cuentasSQLite.cuentasSQLiteOpenHelper;

public class EditarCuentaActivity extends AppCompatActivity {


    EditText edtContrasena,edtNombre;
    Bundle b;
    int id_Cuenta;
    cuentasSQLiteOpenHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cuenta);

        init();

    }
    public void init(){

        //Relacionar campos EditText con la vista
        edtContrasena=(EditText) findViewById(R.id.edtContrasenaEditarCuenta);
        edtNombre=(EditText) findViewById(R.id.edtNombreEditarCuenta);

        //inicializar base de datos de cuentas
        myDB=new cuentasSQLiteOpenHelper(this);

        //obtener el id de la cuenta a editar
        b= getIntent().getExtras();
        id_Cuenta =b.getInt("id_Cuenta");

        //Settear textos a las cajas de las vistas
        ArrayList<Cuenta> cuentaArrayList = myDB.obtenerTodasLasCuentas();
        for (Cuenta cuenta: cuentaArrayList) {
            if(cuenta.getUsuario()==id_Cuenta){
                edtContrasena.setText(cuenta.getContrasena());
                edtNombre.setText(cuenta.getNombre());
            }
        }
    }

    public void Editar(View view){
        int usuario=id_Cuenta;
        String contrasena=edtContrasena.getText().toString().trim();
        String nombre =edtNombre.getText().toString().trim();
        myDB.editarCuenta(usuario,contrasena,nombre);
    }
    public void cancelarVolver(View view){
        Intent i = new Intent(this, CuentasActivity.class);
        finish();
        startActivity(i);
    }
}