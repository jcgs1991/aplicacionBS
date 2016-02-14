package app.sunshine.android.example.cristian.proyectocurso;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PantallaFragment extends Activity implements AdapterView.OnItemSelectedListener  {

    private Spinner spProvincias, spLocalidades;
/*
    String[] categorias= new String[]{
            "Moda y Accesorios",
            "Perfumeria y Cosmética",
            "Hogar y Decoración",
            "Joyeria y Relojes",
            "Optica",
            "Bebes",
            "Deportes",
            "Juguetes"
    };


    int[] foto= new int[]{
            R.drawable.bs,
            R.drawable.bs,
            R.drawable.bs,
            R.drawable.bs,
            R.drawable.bs,
            R.drawable.bs,
            R.drawable.bs,
            R.drawable.bs
    };

    String[] ocurrencias=new String[]{

            "Categoria de moda y accesorias",
            "Categoria de perfumeria y cosmetica",
            "Categoria de Hogar y decoracion",
            "Categoria de Joyeria y Relojes",
            "Categoria de Optica",
            "Categoria de Bebes",
            "Categoria de Deportes",
            "Categoria de Juguetes"
    };
*/

    ListView list;
    String[] itemname ={
            "Safari",
            "Camera",
            "Global",
            "FireFox",
            "UC Browser",
            "Android Folder",
            "VLC Player",
            "Cold War"
    };

    Integer[] imgid={
            R.drawable.bs,
            R.drawable.bs,
            R.drawable.bs,
            R.drawable.bs,
            R.drawable.bs,
            R.drawable.bs,
            R.drawable.bs,
            R.drawable.bs,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_busqueda);
        this.spProvincias = (Spinner) findViewById(R.id.sp_provincia);
        this.spLocalidades = (Spinner) findViewById(R.id.sp_localidad);

        loadSpinnerProvincias();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);

        //Esto si funciona, pantalla_fragment_categoria
        /*
        CustomListAdapter adapter=new CustomListAdapter(this, itemname, imgid);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem= itemname[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

            }
        });
*/
    }


    private void loadSpinnerProvincias() {

        // Create an ArrayAdapter using the string array and a default spinner
        // layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                //IKMPORANTE------R.array.provincias
                this, R.array.categorias, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        this.spProvincias.setAdapter(adapter);

        // This activity implements the AdapterView.OnItemSelectedListener
        this.spProvincias.setOnItemSelectedListener(this);
        this.spLocalidades.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos,
                               long id) {

        switch (parent.getId()) {
            case R.id.sp_provincia:

                // Retrieves an array
                TypedArray arrayLocalidades = getResources().obtainTypedArray(
                        R.array.array_categorias_a_subcategorias);
                //IMPORTANTE------------R.array.array.provincia_a_localidades
                //IMPORTANTE----- CharSequence[] localidades = arrayLocalidades.getTextArray(pos);
                CharSequence[] localidades = arrayLocalidades.getTextArray(pos);
                arrayLocalidades.recycle();

                // Create an ArrayAdapter using the string array and a default
                // spinner layout
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                        this, android.R.layout.simple_spinner_item,
                        android.R.id.text1, localidades);

                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // Apply the adapter to the spinner
                this.spLocalidades.setAdapter(adapter);

                break;

            case R.id.sp_localidad:

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Callback method to be invoked when the selection disappears from this
        // view. The selection can disappear for instance when touch is
        // activated or when the adapter becomes empty.
    }

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pantalla_categoria);


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        //setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);


        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
        for(int i=0;i<10;i++)
        {

            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("txt","Categorias : " + categorias[i]);
            hm.put("cur","Ocurrencias : " + ocurrencias[i]);
            hm.put("foto",Integer.toString(foto[i]));
            aList.add(hm);

        }

        String[] from = {"foto","txt","cur"};
        int[] to = {R.id.flag, R.id.txt,R.id.cur};

        SimpleAdapter adapter= new SimpleAdapter(getBaseContext(), aList,R.layout.lista_pantalla_categoria,from,to);
        ListView listView= (ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);

    }
*/


    public void showLocalidadSelected(View v) {
        Toast.makeText(
                getApplicationContext(),
                getString(R.string.id, spLocalidades.getSelectedItem()
                        .toString(), spProvincias.getSelectedItem().toString()),
                Toast.LENGTH_LONG).show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_busqueda, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        



        return super.onOptionsItemSelected(item);
    }

    public void botonsalir(View view){
        efectopulsado(view);
        finish();
    }

    public void efectopulsado(View view) {

        // show click effect on button pressed
        final AlphaAnimation buttonClick = new AlphaAnimation(0.5F, 0.8F);
        view.startAnimation(buttonClick);

    }

    /*
    private void ElementosLista(){

        String[] misCategorias={"Moda y Accesorios","Perfumeria y Cosmética","Hogar y Decoración","Joyeria y Relojes",
                "Optica","Bebes","Deportes","Juguetes"};

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(
                this,R.layout.fragment_pantalla_categoria,misCategorias

        );

        ListView listView= (ListView)findViewById(R.id.action_settings);
        listView.setAdapter(adapter);

    }
    */




    }


