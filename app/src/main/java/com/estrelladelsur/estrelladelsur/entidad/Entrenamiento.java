package com.estrelladelsur.estrelladelsur.entidad;

public class Entrenamiento {

    private int ID_ENTRENAMIENTO;
    private int ID_CANCHA;
    private String DIA;
    private String HORA;
    private String USUARIO_CREADOR;
    private String FECHA_CREACION;
    private String USUARIO_ACTUALIZACION;
    private String FECHA_ACTUALIZACION;

    public Entrenamiento(int id, String dia, String hora, int id_cancha, String usuario,
                         String fechaCreacion, String usuario_act, String fechaActualizacion) {

        ID_ENTRENAMIENTO = id;
        DIA = dia;
        HORA = hora;
        ID_CANCHA = id_cancha;
        USUARIO_CREADOR = usuario;
        FECHA_CREACION = fechaCreacion;
        USUARIO_ACTUALIZACION = usuario_act;
        FECHA_ACTUALIZACION = fechaActualizacion;
    }

    public String getFECHA_ACTUALIZACION() {
        return FECHA_ACTUALIZACION;
    }

    public void setFECHA_ACTUALIZACION(String FECHA_ACTUALIZACION) {
        this.FECHA_ACTUALIZACION = FECHA_ACTUALIZACION;
    }

    public String getUSUARIO_ACTUALIZACION() {
        return USUARIO_ACTUALIZACION;
    }

    public void setUSUARIO_ACTUALIZACION(String USUARIO_ACTUALIZACION) {
        this.USUARIO_ACTUALIZACION = USUARIO_ACTUALIZACION;
    }

    public String getFECHA_CREACION() {
        return FECHA_CREACION;
    }

    public void setFECHA_CREACION(String FECHA_CREACION) {
        this.FECHA_CREACION = FECHA_CREACION;
    }

    public String getUSUARIO_CREADOR() {
        return USUARIO_CREADOR;
    }

    public void setUSUARIO_CREADOR(String USUARIO_CREADOR) {
        this.USUARIO_CREADOR = USUARIO_CREADOR;
    }

    public int getID_ENTRENAMIENTO() {
        return ID_ENTRENAMIENTO;
    }

    public void setID_ENTRENAMIENTO(int iD_ENTRENAMIENTO) {
        ID_ENTRENAMIENTO = iD_ENTRENAMIENTO;
    }

    public int getID_CANCHA() {
        return ID_CANCHA;
    }

    public void setID_CANCHA(int iD_CANCHA) {
        ID_CANCHA = iD_CANCHA;
    }

    public String getDIA() {
        return DIA;
    }

    public void setDIA(String dIA) {
        DIA = dIA;
    }

    public String getHORA() {
        return HORA;
    }

    public void setHORA(String hORA) {
        HORA = hORA;
    }
}