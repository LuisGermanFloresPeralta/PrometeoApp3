package es.resisg.prometeoapp3.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import es.resisg.prometeoapp3.R;

public class DetallesActuacionesActivity extends AppCompatActivity {

    TextView t1;
    Bundle b;
    String Nombre_profesor,Fecha,Tipo_actuacion,Comentario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_actuaciones);

        b= getIntent().getExtras();
        Nombre_profesor =b.getString("Nombre_profesor");

        t1=(TextView) findViewById(R.id.textView6);
        t1.setText(Nombre_profesor);
    }
    public void volverAtras(View view) {
        onBackPressed();
    }
}