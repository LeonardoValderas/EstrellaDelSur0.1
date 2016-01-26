package com.estrelladelsur.estrelladelsur.entidad;

public class Articulo {

    private int ID_ARTICULO;
    private String TITULO;
    private String ARTICULO;
    private String USUARIO_CREADOR;
    private String FECHA_CREACION;
    private String USUARIO_ACTUALIZACION;
    private String FECHA_ACTUALIZACION;


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

    public String getUSUARIO_ACTUALIZACION() {
        return USUARIO_ACTUALIZACION;
    }

    public void setUSUARIO_ACTUALIZACION(String USUARIO_ACTUALIZACION) {
        this.USUARIO_ACTUALIZACION = USUARIO_ACTUALIZACION;
    }

    public String getTITULO() {
        return TITULO;
    }

    public void setTITULO(String TITULO) {
        this.TITULO = TITULO;
    }

    public String getARTICULO() {
        return ARTICULO;
    }

    public void setARTICULO(String ARTICULO) {
        this.ARTICULO = ARTICULO;
    }

    public String getUSUARIO_CREADOR() {
        return USUARIO_CREADOR;
    }

    public void setUSUARIO_CREADOR(String USUARIO_CREADOR) {
        this.USUARIO_CREADOR = USUARIO_CREADOR;
    }

    public String getFECHA_CREACION() {
        return FECHA_CREACION;
    }

    public void setFECHA_CREACION(String FECHA_CREACION) {
        this.FECHA_CREACION = FECHA_CREACION;
    }

    public String getFECHA_ACTUALIZACION() {
        return FECHA_ACTUALIZACION;
    }

    public void setFECHA_ACTUALIZACION(String FECHA_ACTUALIZACION) {
        this.FECHA_ACTUALIZACION = FECHA_ACTUALIZACION;
    }

    public int getID_ARTICULO() {
        return ID_ARTICULO;
    }

    public void setID_ARTICULO(int ID_ARTICULO) {
        this.ID_ARTICULO = ID_ARTICULO;
    }
}