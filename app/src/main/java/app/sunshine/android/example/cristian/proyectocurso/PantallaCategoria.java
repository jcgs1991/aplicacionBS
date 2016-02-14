package app.sunshine.android.example.cristian.proyectocurso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.ActionBar;


public class PantallaCategoria extends AppCompatActivity {

    Toolbar toolbarCategoria;




    ListView list;
    String[] itemname ={
            "Falda",
            "Pantalon vaquero",
            "Calcetines",
            "Bufandas",
            "Guantes",
            "Zapatos",
            "Jersey",
            "Camisa"
    };

    Integer[] imgid={
            R.drawable.falda,
            R.drawable.vaquero,
            R.drawable.calcetines,
            R.drawable.bufanda,
            R.drawable.guantes,
            R.drawable.zapatos,
            R.drawable.jersey,
            R.drawable.camisajpg,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_categoria);
        toolbarCategoria = (Toolbar) findViewById(R.id.toolbar);


        if (toolbarCategoria != null) {
            toolbarCategoria.setLogo(R.drawable.logo);

            setSupportActionBar(toolbarCategoria);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        final CustomListAdapter adapter=new CustomListAdapter(this, itemname, imgid);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem = itemname[+position];
                //t.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
                String nomProd= adapter.getItem(position);
                int idImg= imgid[+position];
                //String pre=adapterListaImg.getItem(position).precio;
                //String descr=adapterListaImg.getItem(position).descripcion;
                Intent intent=new Intent(getApplicationContext(),Informacion.class).putExtra(Intent.EXTRA_TEXT, nomProd);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("IdImagen", idImg);
                //intent.putExtra("precio",pre);
                //intent.putExtra("Descripcion",descr);
                startActivity(intent);

            }
        });

    }

}
