package app.sunshine.android.example.cristian.proyectocurso;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class InformacionFragment extends Fragment {


    public InformacionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intent=getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_informacion, container, false);
        if (intent!=null && intent.hasExtra(Intent.EXTRA_TEXT)){
            String id=intent.getStringExtra(Intent.EXTRA_TEXT);
            String nombre=intent.getStringExtra("nombre");
            String descr=intent.getStringExtra("Descripcion");
            String imagen=intent.getStringExtra("IdImagen");
            ((TextView) rootView.findViewById(R.id.textviewNombre)).setText(nombre);
            ((TextView) rootView.findViewById(R.id.idProd)).setText(id);
            Picasso.with(getContext()).load(imagen).into(((ImageView)rootView.findViewById(R.id.idImg)));
            ((TextView) rootView.findViewById(R.id.nom_amig)).setText(descr);
            getActivity().setTitle(nombre);
        }

        return rootView;
    }
}
