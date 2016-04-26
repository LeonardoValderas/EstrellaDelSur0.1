package com.estrelladelsur.estrelladelsur.entidad;

public class Articulo {
    private int ID_ARTICULO;
    private String TITULO;
    private String ARTICULO;
    private String USUARIO_CREADOR;
    private String FECHA_CREACION;
    private String USUARIO_ACTUALIZACION;
    private String FECHA_ACTUALIZACION;
    private String FECHA_ARTICULO;

    public Articulo(int id, String titulo, String articulo, String usuario,
                    String fecha_creacion, String usuario_actualizacion, String fecha_actualizacion) {

        ID_ARTICULO = id;
        TITULO = titulo;
        ARTICULO = articulo;
        USUARIO_CREADOR = usuario;
        FECHA_CREACION = fecha_creacion;
        USUARIO_ACTUALIZACION = usuario_actualizacion;
        FECHA_ACTUALIZACION = fecha_actualizacion;
    }
    //USUARIO
    public Articulo(int id, String titulo, String articulo, String fecha_articulo) {

        ID_ARTICULO = id;
        TITULO = titulo;
        ARTICULO = articulo;
        FECHA_ARTICULO = fecha_articulo;
    }
    //USUARIO
    public Articulo(int id, String titulo, String articulo) {

        ID_ARTICULO = id;
        TITULO = titulo;
        ARTICULO = articulo;
    }
    public String getUSUARIO_ACTUALIZACION() {
        return USUARIO_ACTUALIZACION;
    }
    public String getTITULO() {
        return TITULO;
    }
    public String getARTICULO() {
        return ARTICULO;
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
    public int getID_ARTICULO() {
        return ID_ARTICULO;
    }
    public String getFECHA_ARTICULO() {return FECHA_ARTICULO;}
}