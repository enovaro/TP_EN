package com.example.ezequielnovaro.tp_en;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Ezequielnovaro on 08/10/2015.
 */
public class HttpConnect {

    private String url;
    private static HttpClient httpClient;

    public HttpConnect(String url)
    {
        this.url=url;
        if(httpClient==null)
        {
            httpClient = crearHttpClient();
        }
    }


    public String getStrDataByGET() throws ClientProtocolException, IOException
    {
        // tipo de request GET
        HttpGet request = new HttpGet(url);
        // tipo de dato de respuesta esperado
        request.setHeader("Accept","text/plain");

        // ejecutamos el request y creamos el response
        HttpResponse response = httpClient.execute(request);

        // leemos estado
        int statucCode = response.getStatusLine().getStatusCode();
        if(statucCode!= HttpStatus.SC_OK)
        {
            throw new IOException();
        }

        // obtenemos el wraper que contiene la respuesta (HttpEntity)
        HttpEntity entityResponse = response.getEntity();
        //____________________________________________

        // devolvemos la respuesta convertida a String
        return EntityUtils.toString(entityResponse);
        // ___________________________________________
    }

    public byte[] getBytesDataByGET() throws ClientProtocolException, IOException
    {
        // tipo de request GET
        HttpGet request = new HttpGet(url);
        // tipo de dato de respuesta esperado
        request.setHeader("Accept","image/png");

        // ejecutamos el request y creamos el response
        HttpResponse response = httpClient.execute(request);

        // leemos estado
        int statucCode = response.getStatusLine().getStatusCode();
        if(statucCode!= HttpStatus.SC_OK)
        {
            throw new IOException();
        }

        // obtenemos el wraper que contiene la respuesta (HttpEntity)
        HttpEntity entityResponse = response.getEntity();
        //____________________________________________

        // devolvemos la respuesta convertida a String

        /*
        Intento de poner el encoding correcto
        String encodingrespuesta = EntityUtils.getContentCharSet(entityResponse);
        encodingrespuesta = encodingrespuesta == null ? "ISO-8859-1" : encodingrespuesta;
        String rta = EntityUtils.toString(entityResponse, encodingrespuesta);
        return  rta;

        */

        return EntityUtils.toByteArray(entityResponse);
        // ___________________________________________
    }

    private HttpClient crearHttpClient()
    {
        // creamos un registro de esquema datos de la URI y TCP
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        // creamos un esquema indicando http y port 80
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(),80));

        // creamos un objeto HttpParams con los parametros para la conexion
        // partiendo de los parametros por defecto
        HttpParams params = new BasicHttpParams();
        ConnManagerParams.setMaxTotalConnections(params, 5); //5 con max.
        ConnManagerParams.setMaxConnectionsPerRoute(params, new ConnPerRouteBean(5));

        // timeouts
        int timeoutConnection = 10000;
        HttpConnectionParams.setConnectionTimeout(params, timeoutConnection);

        int timeoutSocket = 15000;
        HttpConnectionParams.setSoTimeout(params, timeoutSocket);

        // creamos el ThreadSafe para pasarle al HttpClient
        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);

        HttpClient httpClient = new DefaultHttpClient(cm,params);

        return httpClient;
    }
}
