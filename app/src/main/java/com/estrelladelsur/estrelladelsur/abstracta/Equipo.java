package com.estrelladelsur.estrelladelsur.abstracta;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;

import android.graphics.Bitmap.CompressFormat;

public class Equipo {

    private int ID_EQUIPO;
    private String NOMBRE_EQUIPO;
    private String USUARIO_CREADOR;
    private String FECHA_CREACION;
    private String USUARIO_ACTUALIZACION;
    private String FECHA_ACTUALIZACION;
    private byte[] ESCUDO;

    public Equipo(int id, String nombre, byte[] escudo, String usuario,
                  String fechaCreacion, String usuario_act, String fechaActualizacion) {

        ID_EQUIPO = id;
        NOMBRE_EQUIPO = nombre;
        ESCUDO = escudo;
        USUARIO_CREADOR = usuario;
        FECHA_CREACION = fechaCreacion;
        USUARIO_ACTUALIZACION = usuario_act;
        FECHA_ACTUALIZACION = fechaActualizacion;

    }

    public int getID_EQUIPO() {
        return ID_EQUIPO;
    }

    public void setID_EQUIPO(int ID_EQUIPO) {
        this.ID_EQUIPO = ID_EQUIPO;
    }

    public String toString() {

        return NOMBRE_EQUIPO;
    }

    public String getNOMBRE_EQUIPO() {
        return NOMBRE_EQUIPO;
    }

    public void setNOMBRE_EQUIPO(String NOMBRE_EQUIPO) {
        this.NOMBRE_EQUIPO = NOMBRE_EQUIPO;
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

    public String getUSUARIO_ACTUALIZACION() {
        return USUARIO_ACTUALIZACION;
    }

    public void setUSUARIO_ACTUALIZACION(String USUARIO_ACTUALIZACION) {
        this.USUARIO_ACTUALIZACION = USUARIO_ACTUALIZACION;
    }

    public String getFECHA_ACTUALIZACION() {
        return FECHA_ACTUALIZACION;
    }

    public void setFECHA_ACTUALIZACION(String FECHA_ACTUALIZACION) {
        this.FECHA_ACTUALIZACION = FECHA_ACTUALIZACION;
    }

    public byte[] getESCUDO() {
        return ESCUDO;
    }

    public void setESCUDO(byte[] ESCUDO) {
        this.ESCUDO = ESCUDO;
    }
}
