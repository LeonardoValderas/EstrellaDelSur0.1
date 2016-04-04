package com.estrelladelsur.estrelladelsur.entidad;

public class Noticia {
    private int ID_NOTICIA;
    private String TITULO;
    private String DESCRIPCION;
    private String LINK;
    private String USUARIO_CREADOR;
    private String FECHA_CREACION;
    private String USUARIO_ACTUALIZACION;
    private String FECHA_ACTUALIZACION;

    public Noticia(int id, String titulo,String noticia,String link, String usuario,
                   String fecha_creacion, String usuario_actualizacion, String fecha_actualizacion) {

        ID_NOTICIA = id;
        TITULO = titulo;
        DESCRIPCION = noticia;
        LINK = link;
        USUARIO_CREADOR = usuario;
        FECHA_CREACION = fecha_creacion;
        USUARIO_ACTUALIZACION = usuario_actualizacion;
        FECHA_ACTUALIZACION = fecha_actualizacion;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public String getUSUARIO_ACTUALIZACION() {
        return USUARIO_ACTUALIZACION;
    }
    public String getTITULO() {
        return TITULO;
    }
    public String getUSUARIO_CREADOR() {
        return USUARIO_CREADOR;
    }
    public String getFECHA_CREACION() {
        return FECHA_CREACION;
    }
    public String getFECHA_ACTUALIZACION() {
        return FECHA_ACTUALIZACION;
    }

    public int getID_NOTICIA() {
        return ID_NOTICIA;
    }

    public String getLINK() {
        return LINK;
    }
}