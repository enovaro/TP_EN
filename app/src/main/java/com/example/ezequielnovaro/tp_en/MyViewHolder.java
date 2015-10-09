package com.example.ezequielnovaro.tp_en;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ezequielnovaro on 08/10/2015.
 */
public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    Integer posision;
    TextView titulo;
    TextView descripcion;
    TextView fecha;
    MyItemClick listener;
    ImageView imgview;


    public MyViewHolder(View itemView, MyItemClick listen) {
        super(itemView);
        listener = listen;
        titulo = (TextView)itemView.findViewById(R.id.txttitulo);
        descripcion = (TextView)itemView.findViewById(R.id.txtdesc);
        fecha = (TextView)itemView.findViewById(R.id.txtfecha);
        imgview = (ImageView)itemView.findViewById(R.id.imageView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        this.listener.clickEnNoticia(posision);

    }

    public void setPosition(int p){
        this.posision = p;
    }


}

