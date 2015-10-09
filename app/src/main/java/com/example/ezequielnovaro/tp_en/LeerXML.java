package com.example.ezequielnovaro.tp_en;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ezequielnovaro on 08/10/2015.
 */
public class LeerXML {

    private List<Noticia> listaNoticias;
    private Noticia noti = null;

    public List<Noticia> traerListaNocias (String xml){


        String tag;
        XmlPullParser parser = Xml.newPullParser();
        try {

            parser.setInput(new StringReader(xml));
            int event = parser.getEventType();


            while (event != XmlPullParser.END_DOCUMENT){

                switch (event){

                    case XmlPullParser.START_DOCUMENT:

                        listaNoticias = new ArrayList<Noticia>();

                        break;

                    case XmlPullParser.START_TAG:

                        tag = parser.getName();

                        if (tag.equals("item")) {

                            noti = new Noticia();

                        }else if (noti != null){
                            if (tag.equals("title")){
                                noti.setTitulo(parser.nextText().replaceAll("\n",""));
                            }else if(tag.equals("link")){
                                noti.setLink(parser.nextText());
                            }else if(tag.equals("description")){
                                noti.setDescripcion(parser.nextText().replaceAll("\n",""));
                            }else if(tag.equals("pubDate")){
                                Date date = null;
                                DateFormat formatter;
                                String fec = parser.nextText();
                                fec = fec.substring(5,16);
                                formatter = new SimpleDateFormat("dd MMM yyy");
                                date = formatter.parse(fec);
                                noti.setFecha(date);
                            }else if(tag.equals("enclosure")){
                                String att = parser.getAttributeValue(null,"url");
                                noti.setImagen(att);

                            }
                        }

                        break;

                    case XmlPullParser.END_TAG:

                        tag = parser.getName();

                        if (tag.equals("item") && noti != null){

                            listaNoticias.add(noti);
                            noti = null;
                        }

                        break;

                }

                event = parser.next();

            }



        }catch (Exception e){
            Log.d("log", e.toString());
        }


       return listaNoticias;
    }

}
