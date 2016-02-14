package app.sunshine.android.example.cristian.proyectocurso;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class PantallaPrincipalFragment extends Fragment {



    ArrayAdapter<Elementolista> adapterListaImg;
    ArrayAdapter<String> adapterListaStr;

    private static General totus;


    public PantallaPrincipalFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        RecuperadorJson tusmu = new RecuperadorJson();
        tusmu.execute();

        totus = null;
        try {
            totus = tusmu.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String[] f=new String[totus.novedades.length];

            for (int i=0;i<totus.novedades.length;i++){
                f[i]=totus.novedades[i].nombreProd;
            }

            List<String> list2=new ArrayList<String>(Arrays.asList(f));


        /*
        ArrayList<Elementolista> Objetoslista=new ArrayList<Elementolista>();
               // Objetoslista.add(new Elementolista(R.drawable.fabaj, "Plancha","djshfjdsbhdsfbdsjhfbjdshbfdsfbdhjfs","77€"));
                //Objetoslista.add(new Elementolista(R.drawable.farr,"Maquina","jdshfkjdsbkjbdskjfhdsfgsdkfjhsk","44€"));

        String[] f=new String[Objetoslista.size()];

        for (int i=0;i<Objetoslista.size();i++){
            //f[i]=Objetoslista.get(i).nomProd;
        }


        List<Elementolista> list=new ArrayList<Elementolista>(Objetoslista);
        List<String> list2=new ArrayList<String>(Arrays.asList(f));

        */


        List<Elementolista> listaParaImagenes = new ArrayList<Elementolista>();
        for (int i=0;i< totus.novedades.length; i++){
            listaParaImagenes.add(totus.novedades[i]);
        }

        adapterListaImg =new ArrayAdapter<Elementolista>(getContext(),R.layout.lista, R.id.listabuena, listaParaImagenes);
        adapterListaStr =new ArrayAdapter<String>(getContext(),R.layout.lista, R.id.listabuena,list2);


        View rootView =inflater.inflate(R.layout.fragment_pantalla_principal, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listaNovedades);
        listView.setAdapter(adapterListaStr);



        ListView listView2=(ListView) rootView.findViewById(R.id.listaMasVistos);

        f=new String[totus.masVistas.length];

        for (int i=0;i<totus.masVistas.length;i++){
            f[i]=totus.masVistas[i].nombreProd;
        }

        list2=new ArrayList<String>(Arrays.asList(f));

        adapterListaStr =new ArrayAdapter<String>(getContext(),R.layout.lista, R.id.listabuena,list2);

        listView2.setAdapter(adapterListaStr);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), Informacion.class).putExtra(Intent.EXTRA_TEXT, totus.novedades[position].id);
                intent.putExtra("IdImagen", totus.novedades[position].imagen);
                intent.putExtra("nombre", totus.novedades[position].nombreProd);
                intent.putExtra("Descripcion", totus.novedades[position].nombreAmigable);
                startActivity(intent);

                //Toast.makeText(getActivity(), "totu", Toast.LENGTH_LONG).show();
            }
        });



        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(getActivity(),Informacion.class).putExtra(Intent.EXTRA_TEXT, totus.masVistas[position].id);
                intent.putExtra("IdImagen", totus.masVistas[position].imagen);
                intent.putExtra("nombre",totus.masVistas[position].nombreProd);
                intent.putExtra("Descripcion", totus.masVistas[position].nombreAmigable);
                startActivity(intent);

                //Toast.makeText(getActivity(), "totu", Toast.LENGTH_LONG).show();
            }
        });



        return rootView;
    }

    public class Elementolista {
        String id;
        String nombreProd;
        String nombreAmigable;
        //String descripcion;
        String imagen;
        public Elementolista(String _id, String _nombreProd,String _nombreAmigable, String _imagen){
            id=_id;
            nombreProd =_nombreProd;
            nombreAmigable=_nombreAmigable;
            imagen=_imagen;
        }
        public Elementolista(){

        }

        public Elementolista(String _nombreProd){
            nombreProd=_nombreProd;
        }


    }

    public class categoria{
        String id;
        String nombre;
        Elementolista[] subcategoria;

        public categoria(String _id, String _nombre, Elementolista[] _subcategoria){
            id=_id;
            nombre=_nombre;

            for (int i=0;i<subcategoria.length;i++){
                subcategoria[i]=_subcategoria[i];
            }
        }
    }

    public class General {
        public Elementolista[] novedades;
        public Elementolista[] masVistas;
        public Elementolista[] subcategoria;
        public categoria[] categorias;
    }


    public class RecuperadorJson extends AsyncTask<Void, Void, General> {



        @Override
        protected General doInBackground(Void... params){


            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                String baseUrl = "http://api.baezashop.es";

                Uri builURL = Uri.parse(baseUrl).buildUpon().build();

                URL url = new URL(builURL.toString());

                Log.d("EOPANTALLAJSON", "URL: " + builURL.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
            } catch (IOException e) {
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                Log.d("EOPANTALLAJSON", "ERROR COGIENDO EL JSON: " + e.getMessage()) ;
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                    }
                }
            }

            Log.d("EOPANTALLAJSON", forecastJsonStr);

                return parsearJSON(forecastJsonStr);


            //return null;
        }

        public General parsearJSON(String objeto_json_en_cadena_eo) {

            General totus = new General();


            JSONObject objetoJson = null;
            try {
                objetoJson = new JSONObject(objeto_json_en_cadena_eo);

                JSONArray arrayNovedades = objetoJson.getJSONArray("novedades") ;
                totus.novedades = new Elementolista[arrayNovedades.length()];
                for ( int i = 0 ; i <  arrayNovedades.length(); i++ ) {
                    JSONObject novedad = arrayNovedades.getJSONObject(i);
                    String novedadId = novedad.getString("id");
                    String novedadnombre = novedad.getString("nombre");
                    String novedadnombre_amigable=novedad.getString("nombre_amigable");
                    String novedadImagen=novedad.getString("imagen");

                    totus.novedades[i] = new Elementolista(novedadId,novedadnombre,novedadnombre_amigable,novedadImagen);
                }



                JSONArray arrayMasvistos = objetoJson.getJSONArray("masvistos") ;
                totus.masVistas = new Elementolista[arrayMasvistos.length()];
                for ( int i = 0 ; i <  arrayMasvistos.length(); i++ ) {
                    JSONObject masvisto = arrayMasvistos.getJSONObject(i);
                    String masvistoId = masvisto.getString("id");
                    String masvistonombre = masvisto.getString("nombre");
                    String masvistonombre_amigable=masvisto.getString("nombre_amigable");
                    String masvistoImagen=masvisto.getString("imagen");

                    totus.masVistas[i] = new Elementolista(masvistoId,masvistonombre,masvistonombre_amigable,masvistoImagen);
                }


                JSONArray arrayCategorias = objetoJson.getJSONArray("categorias");
                totus.categorias=new categoria[arrayCategorias.length()];
                for ( int i = 0 ; i <  arrayCategorias.length(); i++ ) {
                    JSONObject categoriaJson = arrayCategorias.getJSONObject(i);
                    String categoriaId = categoriaJson.getString("id");
                    String categorianombre = categoriaJson.getString("nombre");

                    JSONArray subcategoria= categoriaJson.getJSONArray("subcategoria");
                    totus.subcategoria=new Elementolista[subcategoria.length()];
                    for(int j=0;j<subcategoria.length();j++){
                        JSONObject subcat= subcategoria.getJSONObject(j);
                        String subcategoriaNombre=subcat.getString("nombre");

                        totus.subcategoria[j]=new Elementolista(subcategoriaNombre);
                    }
                    totus.categorias[i]=new categoria(categoriaId,categorianombre,totus.subcategoria);
                }


            }
            catch (JSONException e) {
                e.printStackTrace();
                Log.e("TE JODES", "HIJO DE PUTA");
            }

            return totus;
        }

    }


}
