package app.sunshine.android.example.cristian.proyectocurso;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PantallaPrincipal extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ExpandableListView mDrawerExpandableList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    Toolbar toolbar;


    private List<String> grupos;
    private HashMap<String, List<String>> datosGrupos;
    private int ultimaPosicionExpList = -1;
    private boolean evitarInicio = false;
    private int ultimaMarcaPosicionGrupo = -1;
    private int ultimaMarcaPosicionHijo = -1;
    private MyAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            toolbar.setLogo(R.drawable.logo);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        funcionDrawer(savedInstanceState);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pantalla_principal, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.informacion) {
            AlertDialog alertDialog;
            alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Informacion");
            alertDialog.setMessage("Aplicacion creada por Juanca y Cristian.");
            alertDialog.setButton(alertDialog.BUTTON_POSITIVE, "Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }

            });
            alertDialog.show();
        }

        if (id == R.id.salir) {
            finish();
        }

        if (id==R.id.action_settings){
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void botonsalir(View view) {
        finish();
    }



    public void funcionDrawer(Bundle savedInstanceState){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerExpandableList = (ExpandableListView) findViewById(R.id.left_drawer);
        mDrawerExpandableList.setGroupIndicator(null);

        View header=getLayoutInflater().inflate(R.layout.cabecera_general,null);
        mDrawerExpandableList.addHeaderView(header, null, false);


        cargarDatos();
        if (toolbar != null) {
            toolbar.setTitle(mDrawerTitle);
            toolbar.setSubtitle(mTitle);
            toolbar.setLogo(R.drawable.logo);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View view) {
                //Si cerramos menú, mostramos título y subtítulo
                getSupportActionBar().setTitle(mDrawerTitle);
                getSupportActionBar().setSubtitle(mTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //Si abrimos menú, personalizamos la acción
                getSupportActionBar().setTitle("Menú");
                getSupportActionBar().setSubtitle("Selecciona opción");
                invalidateOptionsMenu();

            }

        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerExpandableList.setTextFilterEnabled(true);

        mDrawerExpandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                if (ultimaPosicionExpList != -1 && groupPosition != ultimaPosicionExpList) {
                    //Cuando abrimos un grupo se cierra el anterior que estuviera abierto
                    mDrawerExpandableList.collapseGroup(ultimaPosicionExpList);
                }
                ultimaPosicionExpList = groupPosition;

                abrirUltPosMarc(groupPosition);//Nos mostrará al abrir el grupo, la última selección marcada
            }
        });

        mDrawerExpandableList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                cerrarUltPosMarc(groupPosition);
            }
        });

        mDrawerExpandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                int grup_pos = (int) adapter.getGroupId(groupPosition);
                //Al seleccionar sobre antiguo grupo 1 marcamos con color (list_selector) y lanzamos
                switch (grup_pos) {
                    case 0:
                        displayView1(0);
                        ultimaMarcaPosicionGrupo = grup_pos;
                        ultimaMarcaPosicionHijo = -1;
                        break;
                    default:
                        break;
                }

                return false;
            }
        });

        mDrawerExpandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                int grup_pos = (int) adapter.getGroupId(groupPosition);
                int child_pos = (int) adapter.getChildId(groupPosition, childPosition);
                //Al seleccionar, se marcará el hijo con un color, contamos para ello con lis_selector2, ver values/color y ...bg_normal más ...bg_pressed
                switch (grup_pos) {
                    ////El siguiente case lo dejamos sin uso, ya que grupo 1 no tiene actualmente hijos
                    /*case 0:
                        switch (child_pos) {
                            case 0:
                                displayView1(0);
                                displayView2(parent, 0, 0);
                                Log.e("Aviso", "El Grupo es: " + grup_pos + " y el hijo es: " + child_pos);
                                break;
                            case 1:
                                displayView1(1);
                                displayView2(parent, 0, 1);
                                Log.e("Aviso", "El Grupo es: " + grup_pos + " y el hijo es: " + child_pos);
                                break;
                            default:
                                break;
                        }
                        break;*/
                    case 1:
                        switch (child_pos) {
                            case 0:
                                displayView1(2);
                                displayView2(parent, 1, 0);
                                Log.e("Aviso", "El Grupo es: " + grup_pos + " y el hijo es: " + child_pos);
                                break;
                            case 1:
                                displayView1(3);
                                displayView2(parent, 1, 1);
                                Log.e("Aviso", "El Grupo es: " + grup_pos + " y el hijo es: " + child_pos);
                                break;
                            case 2:
                                displayView1(4);
                                displayView2(parent, 1, 2);
                                Log.e("Aviso", "El Grupo es: " + grup_pos + " y el hijo es: " + child_pos);
                                break;
                            default:
                                break;
                        }
                        break;
                    case 2:
                        switch (child_pos) {
                            case 0:
                                displayView1(5);
                                displayView2(parent, 2, 0);
                                Log.e("Aviso", "El Grupo es: " + grup_pos + " y el hijo es: " + child_pos);
                                break;
                            default:
                                break;
                        }
                    default:
                        break;
                }
                ultimaMarcaPosicionGrupo = grup_pos;
                ultimaMarcaPosicionHijo = child_pos;
                mDrawerLayout.closeDrawer(mDrawerExpandableList);
                return false;
            }

        });

        if (savedInstanceState == null) {
            displayView1(0);//Se inicia la app llamando a la posición del fragment  y marcando grupo 1
            evitarInicio = true;
        }
    }


    private void cargarDatos() {

        grupos = new ArrayList<String>();
        datosGrupos = new HashMap<String, List<String>>();

        grupos.add("Electronica");
        grupos.add("Moda");
        grupos.add("Consolas");

        List<String> hijos_grupo1 = new ArrayList<String>();

        List<String> hijos_grupo2 = new ArrayList<String>();
        hijos_grupo2.add("Niños");
        hijos_grupo2.add("Hombres");
        hijos_grupo2.add("Mujeres");

        List<String> hijos_grupo3 = new ArrayList<String>();
        hijos_grupo3.add("Videojuegos");
        hijos_grupo3.add("Consolas");


        datosGrupos.put(grupos.get(0), hijos_grupo1);
        datosGrupos.put(grupos.get(1), hijos_grupo2);
        datosGrupos.put(grupos.get(2), hijos_grupo3);

        adapter = new MyAdapter(this, grupos, datosGrupos);
        mDrawerExpandableList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void abrirUltPosMarc(int groupPosition) {

        if (groupPosition == ultimaMarcaPosicionGrupo) {
            if (ultimaMarcaPosicionHijo != -1) {
                int index = mDrawerExpandableList.getFlatListPosition(ExpandableListView.getPackedPositionForChild(ultimaMarcaPosicionGrupo, ultimaMarcaPosicionHijo));
                mDrawerExpandableList.setItemChecked(index, true);
            } else {
                int index = mDrawerExpandableList.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(ultimaMarcaPosicionGrupo));
                mDrawerExpandableList.setItemChecked(index, true);
            }
        } else {
            if (mDrawerExpandableList.isGroupExpanded(ultimaMarcaPosicionGrupo)) {
                if (ultimaMarcaPosicionHijo != -1) {
                    int index = mDrawerExpandableList.getFlatListPosition(ExpandableListView.getPackedPositionForChild(ultimaMarcaPosicionGrupo, ultimaMarcaPosicionHijo));
                    mDrawerExpandableList.setItemChecked(index, true);
                } else {
                    int index = mDrawerExpandableList.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(ultimaMarcaPosicionGrupo));
                    mDrawerExpandableList.setItemChecked(index, true);
                }
            } else {
                mDrawerExpandableList.setItemChecked(-1, true);
            }
        }
    }


    private void cerrarUltPosMarc(int groupPosition) {

        if (groupPosition == ultimaMarcaPosicionGrupo) {
            if (ultimaMarcaPosicionGrupo != -1) {
                int index = mDrawerExpandableList.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(ultimaMarcaPosicionGrupo));
                mDrawerExpandableList.setItemChecked(index, true);
            } else {
                mDrawerExpandableList.setItemChecked(-1, true);
            }
        }
        if (mDrawerExpandableList.isGroupExpanded(ultimaMarcaPosicionGrupo)) {
            if (ultimaMarcaPosicionHijo != -1) {
                int index = mDrawerExpandableList.getFlatListPosition(ExpandableListView.getPackedPositionForChild(ultimaMarcaPosicionGrupo, ultimaMarcaPosicionHijo));
                mDrawerExpandableList.setItemChecked(index, true);
            } else {
                int index = mDrawerExpandableList.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(ultimaMarcaPosicionGrupo));
                mDrawerExpandableList.setItemChecked(index, true);
            }
        } else {
            mDrawerExpandableList.setItemChecked(-1, true);

        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void displayView2 (ExpandableListView parent, int groupPosition, int childPosition) {

        int index;
        index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
        parent.setChoiceMode(ExpandableListView.CHOICE_MODE_MULTIPLE);

        parent.setItemChecked(index, true);

        ultimaMarcaPosicionGrupo = groupPosition;
        ultimaMarcaPosicionHijo = childPosition;
        //Ejemplo enumerado del porqué de los index:
                /*
                Grupo 1 [index 0]

                    Hijo 1 [index 1]
                    Hijo 2 [index 2]

                Grupo 2 [index 3]

                    Hijo 1 [index 4]
                    HIjo 2 [index 5]
                    Hijo 3 [index 6]

                Grupo 3 [index 7]
                    ...
                */
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void displayView1 (int position) {

        Fragment fragment = null;
        mDrawerExpandableList.setChoiceMode(ExpandableListView.CHOICE_MODE_MULTIPLE);
        int childCount = mDrawerExpandableList.getChildCount();

        switch (position) {

            case 1:
                //fragment = new InformacionFragment();
                mDrawerTitle = "Electronica";
                if (evitarInicio == true) {//Evitamos el borrado de la marca en el inicio de la app
                    mDrawerExpandableList.clearChoices();
                }
                mDrawerExpandableList.expandGroup(0);//Opcional mostramos el grupo inicial expandido
                mDrawerExpandableList.setItemChecked(1, true);//En el inicio y al seleccionar hijo nos marca el grupo 1

                for (int i = 0; i < childCount; i++) {
                    if (i != 1) {
                        mDrawerExpandableList.setItemChecked(i, false);
                    }
                }
                break;
            //El siguiente case lo dejamos sin uso, ya que grupo 1 no tiene actualmente más hijos
            /*case 1:
                fragment = new Fm_2();
                mDrawerTitle = "Grupo 1";
                mTitle = "Hijo 2 Grupo 1";
                mDrawerExpandableList.clearChoices();
                mDrawerExpandableList.setItemChecked(1, true);
                //mDrawerExpandableList.setItemChecked(2, false);//Opcional aunque mejor un for
                //mDrawerExpandableList.setItemChecked(0, false);// "   "    "  "    "    "  "
                for (int i = 0; i < childCount; i++) {
                    if (i != 1) {
                        mDrawerExpandableList.setItemChecked(i, false);
                    }
                }
                break;*/
            case 2:

                   // ----------ESTO FUNCIONA---------

                mDrawerTitle = "Moda";
                mTitle = "Niños";
                Intent n=new Intent(getApplicationContext(),PantallaCategoria.class).putExtra(Intent.EXTRA_TEXT,mDrawerTitle);
                n.putExtra("subtitulo",mTitle);
                startActivity(n);
                mDrawerExpandableList.clearChoices();
                mDrawerExpandableList.setItemChecked(2, true);

                for (int i = 0; i < childCount; i++) {
                    if (i != 2) {
                        mDrawerExpandableList.setItemChecked(i, false);
                    }
                }
                break;
            case 3:
                //fragment = new Fm_3();
                mDrawerTitle = "Moda";
                mTitle = "Hombres";
                mDrawerExpandableList.clearChoices();
                mDrawerExpandableList.setItemChecked(2, true);
                for (int i = 0; i < childCount; i++) {
                    if (i != 2) {
                        mDrawerExpandableList.setItemChecked(i, false);
                    }
                }
                break;
            case 4:
                //fragment = new Fm_4();
                mDrawerTitle = "Moda";
                mTitle = "Mujeres";
                mDrawerExpandableList.clearChoices();
                mDrawerExpandableList.setItemChecked(2, true);

                for (int i = 0; i < childCount; i++) {
                    if (i != 2) {
                        mDrawerExpandableList.setItemChecked(i, false);
                    }
                }
                break;
            case 5:
                //fragment = new Fm_5();
                mDrawerTitle = "Consolas";
                mTitle = "Videojuegos";
                mDrawerExpandableList.clearChoices();
                mDrawerExpandableList.setItemChecked(3, true);

                for (int i = 0; i < childCount; i++) {
                    if (i != 3) {
                        mDrawerExpandableList.setItemChecked(i, false);
                    }
                }
                break;
            default:
                mDrawerTitle = "Baeza Shop";

                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();

            mDrawerExpandableList.setSelection(position);
            getSupportActionBar().setTitle(mDrawerTitle);
            getSupportActionBar().setSubtitle(mTitle);
            mDrawerLayout.closeDrawer(mDrawerExpandableList);
        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }








}
