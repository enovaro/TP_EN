package com.example.ezequielnovaro.tp_en;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by Ezequielnovaro on 08/10/2015.
 */
public class ThreadConnect implements Runnable {

    private String url;
    private boolean flagBytesString;
    private Handler h;

    public ThreadConnect(String url, boolean flagBytesString, Handler h)
    {
        this.url = url;
        this.flagBytesString=flagBytesString;
        this.h = h;
    }

    @Override
    public void run() {
        Message msg = new Message();
        msg.arg1=0;

        HttpConnect httpManager = new HttpConnect(url);
        try{

            if(flagBytesString)
            {
                byte[] bytesRespuesta;
                bytesRespuesta = httpManager.getBytesDataByGET();
                // cargar respuesta en mensaje
                msg.arg1=1;
                msg.obj = bytesRespuesta;

            }
            else
            {
                String strRespuesta;
                strRespuesta = httpManager.getStrDataByGET();
                //strRespuesta = EntityUtils.getContentCharSet()
                // cargar respuesta en mensaje
                msg.arg1=2;
                msg.obj = strRespuesta;
            }

        }catch(Exception e)
        {
            Log.d("http", "ERROR");
            msg.arg1=0;
        }

        // Enviar mensaje
        h.sendMessage(msg);
    }
}
