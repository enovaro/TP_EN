package com.example.ezequielnovaro.tp_en;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by Ezequielnovaro on 08/10/2015.
 */
public class HilosImg implements Runnable {

    private String url;
    private int ind;
    private Handler h;


    /**
     * Constructor por defecto
     * @param url link donde va a buscar la imagen
     * @param h Es el handler enviado desde la Activity principal
     * @param pos Es la posicion que ocupa la imagen dentro de la lista de noticias
     */
    public HilosImg(String url, Handler h, int pos)
    {
        this.url = url;
        this.h = h;
        this.ind =pos;
    }


    @Override
    public void run() {

        Message msg = new Message();
        msg.arg1=0;

        HttpConnect httpManager = new HttpConnect(url);
        try{

                byte[] bytesRespuesta;
                bytesRespuesta = httpManager.getBytesDataByGET();
                // cargar respuesta en mensaje
                msg.arg1=1;
                msg.arg2=this.ind;
                msg.obj = bytesRespuesta;
                Log.d("activity", "Se cargo en el msg.obj");


        }catch(Exception e)
        {
            Log.d("Activit", "HTTP ERROR");
            msg.arg1=0;
        }


        // Enviar mensaje
        //Envio el array de la imagen y la posicion que ocupa la noticia dentor de la lista
        h.sendMessage(msg);
        Log.d("activity", "Se envio msg con imagen");

    }
}
