package com.estrelladelsur.estrelladelsur.entidad;

public class Cancha {
    private int ID_CANCHA;
    private String NOMBRE;
    private String LONGITUD;
    private String LATITUD;
    private String DIRECCION;
    private String USUARIO_CREADOR;
    private String FECHA_CREACION;
    private String USUARIO_ACTUALIZACION;
    private String FECHA_ACTUALIZACION;

    public String getUSUARIO_CREADOR() {
        return USUARIO_CREADOR;
    }

    public String getUSUARIO_ACTUALIZACION() {
        return USUARIO_ACTUALIZACION;
    }

    public Cancha(int id, String nombre, String logitud, String latitud, String direccion, String usuario,
                  String fechaCreacion, String usuario_act, String fechaActualizacion) {
        ID_CANCHA = id;
        NOMBRE = nombre;
        LONGITUD = logitud;
        LATITUD = latitud;
        DIRECCION = direccion;
        USUARIO_CREADOR = usuario;
        FECHA_CREACION = fechaCreacion;
        USUARIO_ACTUALIZACION = usuario_act;
        FECHA_ACTUALIZACION = fechaActualizacion;
    }

    public Cancha(int id, String nombre, String logitud, String latitud, String direccion) {
        ID_CANCHA = id;
        NOMBRE = nombre;
        LONGITUD = logitud;
        LATITUD = latitud;
        DIRECCION = direccion;
    }

    public int getID_CANCHA() {
        return ID_CANCHA;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public String toString() {
        return NOMBRE;
    }

    public String getLONGITUD() {
        return LONGITUD;
    }

    public String getLATITUD() {
        return LATITUD;
    }

    public String getDIRECCION() {
        return DIRECCION;
    }

    public String getFECHA_CREACION() {
        return FECHA_CREACION;
    }

    public String getFECHA_ACTUALIZACION() {
        return FECHA_ACTUALIZACION;
    }
}