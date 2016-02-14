package app.sunshine.android.example.cristian.proyectocurso;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by Usuario on 10/02/2016.
 */
public class PantallaBusquedaFragment extends Fragment {

    public PantallaBusquedaFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_pantalla_categoria,container,false);
    }
}
