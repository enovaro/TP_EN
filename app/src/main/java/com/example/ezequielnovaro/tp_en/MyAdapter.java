package com.example.ezequielnovaro.tp_en;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Ezequielnovaro on 08/10/2015.
 */
public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<Noticia> listaNoticias;
    private MyItemClick listener;


    public MyAdapter(List<Noticia> list, MyItemClick clik){

        this.listaNoticias = list;
        this.listener = clik;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, viewGroup, false);
        MyViewHolder vh = new MyViewHolder(v,this.listener);
        return vh;

    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {

        //Cargo los items de la View
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
        Noticia noticia = listaNoticias.get(i);
        myViewHolder.titulo.setText(noticia.getTitulo());
        myViewHolder.descripcion.setText(noticia.getDescripcion());
        myViewHolder.fecha.setText(dateFormat.format(noticia.getFecha()));
        myViewHolder.setPosition(i);

        //Si el atributo imagen array tiene data, se debe cargar la imagen.
        if (noticia.imagenarray != null){
            Log.d("activity", "Se carga imagen en ImgView");
            Bitmap bip = BitmapFactory.decodeByteArray(noticia.imagenarray, 0, noticia.imagenarray.length);
            myViewHolder.imgview.setImageBitmap(bip);
        }


    }


    /**
     * Metodo que devuelve la cantidad de items en la lista
     * @return El numero de Items
     */
    @Override
    public int getItemCount() {
        return this.listaNoticias.size();
    }
}
