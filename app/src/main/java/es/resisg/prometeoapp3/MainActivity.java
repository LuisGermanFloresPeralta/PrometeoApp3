package es.resisg.prometeoapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    //Atributos
    EditText edtUsuario,edtContrasena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //relacionar atributos con la parte gráfica
        edtUsuario =(EditText) findViewById(R.id.edtUsuario_main_layout);
        edtContrasena =(EditText) findViewById(R.id.edtContrasena_main_layout);

    }

    //metodo de accesi
    public void Aceder(View view){

    }
}