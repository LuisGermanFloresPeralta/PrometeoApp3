package es.resisg.prometeoapp3.controlador.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.controlador.ConectadoActivity.ConectadoActivity;
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
            String respuesta = new Conectar().execute(validaUsuarioURL,usuario,contrasena).get();
            if (esVacio(usuario,contrasena)){
                Toast.makeText(this, "Tienes que rellenar todos los campos", Toast.LENGTH_SHORT).show();
            }else if(noExisteUsuario(respuesta)){
                Toast.makeText(this,"Usuario no registrado, hable con secretaria",Toast.LENGTH_SHORT).show();
            }else{
                //añadimos el nombre,usuario,contrasena al siguiente activity
                Toast.makeText(this,"¡Bienvenido/a! "+respuesta,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, ConectadoActivity.class);
                i.putExtra("nombre",respuesta);
                i.putExtra("usuario",edtUsuario.getText().toString());
                i.putExtra("contrasena",edtContrasena.getText().toString());
                startActivity(i);
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    //Se comprueba si estaVacio los campos usuario y contrasena
    public boolean esVacio(String usuario, String contrasena){
        if (usuario.isEmpty() || contrasena.isEmpty()){
            return  true;
        }else {
            return false;
        }
    }
    //comprueba si el usuario no existe en el servidor
    public boolean noExisteUsuario(String respuetaServer){
        if(respuetaServer.equals("\uFEFF\uFEFF\uFEFF0")) {
            return true;
        }
        return false;
    }
}