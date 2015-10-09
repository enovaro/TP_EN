package com.example.ezequielnovaro.tp_en;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;
import java.util.concurrent.ExecutorService;


public class MainActivity extends ActionBarActivity implements Handler.Callback, MyItemClick, View.OnClickListener {

    private List<Noticia> listaNocitias = null;
    public RecyclerView recycler;
    ExecutorService executor;
    Handler hand = new Handler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnbuscar = (Button)findViewById(R.id.btnLeer);
        btnbuscar.setOnClickListener(this);

        recycler = (RecyclerView)this.findViewById(R.id.vernoticias);
        recycler.setHasFixedSize(true);
        LinearLayoutManager linear = new LinearLayoutManager(this);
        recycler.setLayoutManager(linear);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch(msg.arg1)
        {
            case 0:{
                Log.d("activity", "Error");
                break;
            }
            case 1:{
                Log.d("activity","Recibiendo bytes (imagen)");
                // Se carga el array de Bytes en la noticia
                byte[] data = (byte[])msg.obj;
                listaNocitias.get(msg.arg2).imagenarray = data;
                Log.d("activity","Se cargo (imagen)");
                break;
            }
            case 2:{


                //Acá llega el msg desde el ThreadConnect y se lo parsea para generar una lista
                //de noticias

                LeerXML leer = new LeerXML();
                listaNocitias = leer.traerListaNocias(msg.obj.toString());

                //Acá se lanzan los Threads para traer el Array de Bytes de las imágenes, las cuales seran
                //tomadas por este mismo Handler (Case 1)

                int count =0;
                for (Noticia n: listaNocitias){
                    //Verifico que tenga un link de imagen almacenado
                    if (n.getImagen() != null){
                        HilosImg hi = new HilosImg(n.getImagen(),hand, count);
                        Thread t = new Thread(hi);
                        t.start();
                        Log.d("activity", "Se lanza thread"+ count);
                    }
                    count ++;
                }

                // Se crea el adapter con sus respectivos parametros
                //FIXME no se si es donde se debe crear
                MyAdapter adp = new MyAdapter(listaNocitias, this);
                recycler.setAdapter(adp);
                Log.d("activity", "Entra al for");

                //Log.d("activity", "Llego: " + msg.obj.toString());
                break;
            }
        }
        return true;
    }


    /**
     * Acá se ejecuta la nueva activity con la web
     * @param pos Puntero que indica en cual noticia se hizo clic para obtener el link
     */
    @Override
    public void clickEnNoticia(int pos) {

        Noticia not = listaNocitias.get(pos);
        Intent i = new Intent(this, Web_Activity.class);
        i.putExtra("direccion",not.getLink());
        startActivity(i);

        Log.d("Act", not.getLink().toString());

    }

    @Override
    public void onClick(View v) {
        Log.d("Act","Clic en boton");

        String web ="";

        EditText texto = (EditText)findViewById(R.id.editText);
        web = texto.getText().toString();

        //verifico si la web se escribio con HTTP o no
        if (!web.contains("http://")){
            web = "http://" + web;
        }

        Log.d("Act",web);

        //Lanzo el Thread para leer el contenido de la página.
        ThreadConnect tc = new ThreadConnect(web,false,hand);
        Thread t = new Thread(tc);
        t.start();

    }
}
