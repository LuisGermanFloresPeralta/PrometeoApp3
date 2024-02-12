package es.resisg.prometeoapp3.controlador.ActivityConectado.fragmetos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.resisg.prometeoapp3.R;

public class ActuacionesParticularesFragment extends Fragment {
    public ActuacionesParticularesFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_actuaciones_particulares, container, false);
    }
}