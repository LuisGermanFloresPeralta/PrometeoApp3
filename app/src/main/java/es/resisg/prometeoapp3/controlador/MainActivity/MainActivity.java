package es.resisg.prometeoapp3.controlador.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.controlador.ActivityConectado.ConectadoActivity;
import es.resisg.prometeoapp3.modelo.conexion.Conectar;

public class MainActivity extends AppCompatActivity {
    //Atributos
    EditText edtUsuario,edtContrasena;
    final String validaUsuarioURL= "http://www.ieslassalinas.org/APP/appValidaUsuario.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //relacionar atributos con la parte gráfica
        edtUsuario =(EditText) findViewById(R.id.edtUsuario_main_layout);
        edtContrasena =(EditText) findViewById(R.id.edtContrasena_main_layout);

    }

    //metodo de acceso
    public void Aceder(View view){
        //tomamos el texto de los TextViews presentes en la parte gráfica.
        String usuario = edtUsuario.getText().toString();
        String contrasena = edtContrasena.getText().toString();


        try {
            //Llamos a la conexion que nos devuelve la respuesta del servidor(nombre del usuario) en String y lo mostramos en un Toast
            String respuesta = new Conectar().execute(validaUsuarioURL,usuario,contrasena).get();
            Toast.makeText(this, respuesta, Toast.LENGTH_SHORT).show();

            //iniciamos el siguiente activity 'ActivityConectado'
            Intent i = new Intent(this, ConectadoActivity.class);
            startActivity(i);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}