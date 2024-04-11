package es.resisg.prometeoapp3.controlador.ConectadoActivity.fragmetos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import es.resisg.prometeoapp3.R;
import es.resisg.prometeoapp3.clases.Evaluacion;
import es.resisg.prometeoapp3.modelo.GestionSesion;
import es.resisg.prometeoapp3.modelo.conexionHTTP.peticiones;

public class NotasFragment extends Fragment {
    public NotasFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private TextView txtPruevaJson;
    private ArrayList<Evaluacion> evaluacionArrayList = new ArrayList<>();
    private GestionSesion gestionSesion;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Infalmos el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_notas, container, false);

        //Recuperamos datos de la sesion(usuario y contraseña)
        gestionSesion = new GestionSesion(getContext());

        // llamamos a conexion.tareas.conseguirEvaluaciones pasando la URL,Usuario,Contraseña y conseguimos un ArrayList<actuacionParticular
        evaluacionArrayList = new peticiones("http://192.168.1.50/WEB/APP/NotasLuisPrueva.php", String.valueOf(gestionSesion.getUsuario()), gestionSesion.getContrasena()).conseguirEvaluaciones();

        //relacionamos los componentes de la parte gráfica oon la parte lógica de la aplicacion y probamos
        txtPruevaJson = view.findViewById(R.id.txtPruevaJson);
        txtPruevaJson.setText(evaluacionArrayList.get(0).getEvaluacion());


        // Devolvemos la vista
        return view;
    }
}