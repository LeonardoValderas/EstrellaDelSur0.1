package com.estrelladelsur.estrelladelsur.entidad;

public class Notificacion {
    private int ID_NOTIFICACION;
    private String TITULO;
    private String NOTIFICACION;
    private String USUARIO_CREADOR;
    private String FECHA_CREACION;
    private String USUARIO_ACTUALIZACION;
    private String FECHA_ACTUALIZACION;

    public Notificacion(int id, String titulo, String notificacion, String usuario,
                        String fecha_creacion, String usuario_actualizacion, String fecha_actualizacion) {

        ID_NOTIFICACION = id;
        TITULO = titulo;
        NOTIFICACION = notificacion;
        USUARIO_CREADOR = usuario;
        FECHA_CREACION = fecha_creacion;
        USUARIO_ACTUALIZACION = usuario_actualizacion;
        FECHA_ACTUALIZACION = fecha_actualizacion;
    }
    public Notificacion(int id, String titulo, String notificacion) {

        ID_NOTIFICACION = id;
        TITULO = titulo;
        NOTIFICACION = notificacion;
    }


    public int getID_NOTIFICACION() {
        return ID_NOTIFICACION;
    }

    public String getNOTIFICACION() {
        return NOTIFICACION;
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

}