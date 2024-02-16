package es.resisg.prometeoapp3.controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import es.resisg.prometeoapp3.R;

public class DetallesItemActuacionesActivity extends AppCompatActivity {

    TextView txtViewNombreProfesor,txtViewFecha,txtViewTipoActuacion,txtViewCometario,txtViewLogoInicial;
    Bundle b;
    String Nombre_profesor,Fecha,Tipo_actuacion,Comentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_item_actuaciones);

        //recojemos los String_extras
        b= getIntent().getExtras();
        Nombre_profesor =b.getString("Nombre_profesor");
        Fecha =b.getString("Fecha");
        Tipo_actuacion =b.getString("Tipo_actuacion");
        Comentario =b.getString("Comentario");

        //asociamos los elemetos con la parte grafica de la aplicacion
        txtViewNombreProfesor= (TextView)findViewById(R.id.txtViewNombreProfesorDetallesItem);
        txtViewFecha=(TextView) findViewById(R.id.txtViewFechaDetallesItem);
        txtViewTipoActuacion=(TextView) findViewById(R.id.txtViewTipoActuacionDetallesItem);
        txtViewCometario=(TextView) findViewById(R.id.txtViewComentarioDetallesItem);
        txtViewLogoInicial=(TextView) findViewById(R.id.txtViewLogoInicialDetallesItem);
        settearTextos();

    }
    //metado para boton back
    public void volverAtras(View view) {
        onBackPressed();
    }

    //colocar textos en el layout
    public void settearTextos(){
        txtViewNombreProfesor.setText(Nombre_profesor);
        txtViewFecha.setText(Fecha);
        txtViewTipoActuacion.setText(Tipo_actuacion);
        txtViewCometario.setText(Comentario);
        txtViewLogoInicial.setText(obtenerInicial(Nombre_profesor));
    }
    //conseguir inicial del nombre
    private String obtenerInicial(String nombre_profesor) {
        String inicial=String.valueOf(nombre_profesor.charAt(1));
        return inicial;
    }
}