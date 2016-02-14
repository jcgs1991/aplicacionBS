package app.sunshine.android.example.cristian.proyectocurso;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class PantallaCategoriaFragment extends Fragment {

    public PantallaCategoriaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        Intent intent=getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_pantalla_categoria, container, false);
        if (intent!=null && intent.hasExtra(Intent.EXTRA_TEXT)){
            String info=intent.getStringExtra(Intent.EXTRA_TEXT);
            String sub=intent.getStringExtra("subtitulo");
            /*String precio=intent.getStringExtra("precio");
            String descr=intent.getStringExtra("Descripcion");
            int im=intent.getIntExtra("IdImagen", 0);
            ((TextView) rootView.findViewById(R.id.textviewNombre)).setText(info);
            ((TextView) rootView.findViewById(R.id.precio)).setText(precio);
            ((ImageView)rootView.findViewById(R.id.idprueba)).setImageResource(im);
            ((TextView) rootView.findViewById(R.id.descripcion)).setText(descr);
            */
            ((Toolbar) rootView.findViewById(R.id.toolbar)).setTitle(info);
            ((Toolbar) rootView.findViewById(R.id.toolbar)).setSubtitle(sub);


        }

        return rootView;
    }
}
