package com.example.ezequielnovaro.tp_en;

import java.util.Date;

/**
 * Created by Ezequielnovaro on 08/10/2015.
 */
public class Noticia {

    private String titulo;
    private String descripcion;
    private Date fecha;
    private String link;
    private String imagen;
    public byte[] imagenarray;

    /**
     * Constructor por defecto
     */
    public Noticia(){

    }

    /**
     * Contructor con parámetros
     * @param tit Título de la noticia
     * @param desc Descripción de la noticia
     * @param fechaNot Fecha de la noticia
     * @param link Link de la noticia
     * @param img Link de la imagen de la noticia
     */
    public Noticia(String tit, String desc, Date fechaNot, String link, String img){

        this.titulo = tit;
        this.descripcion = desc;
        this.fecha = fechaNot;
        this.link = link;
        this.imagen = img;

    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
