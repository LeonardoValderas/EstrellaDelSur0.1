package com.estrelladelsur.estrelladelsur.entidad;

import java.util.ArrayList;

public class EntrenamientoRecycler {

    private int ID_ENTRENAMIENTO;
    private int ID_CANCHA;
    private String NOMBRE;
    private String DIA;
    private String HORA;

    public EntrenamientoRecycler(int id, String dia, String hora, int id_cancha, String nombre) {
        ID_ENTRENAMIENTO = id;
        DIA = dia;
        HORA = hora;
        ID_CANCHA = id_cancha;
        NOMBRE = nombre;
    }

    public int getID_ENTRENAMIENTO() {
        return ID_ENTRENAMIENTO;
    }
    public int getID_CANCHA() {
        return ID_CANCHA;
    }
    public String getNOMBRE() {
        return NOMBRE;
    }
    public String getDIA() {
        return DIA;
    }
    public String getHORA() {
        return HORA;
    }
    public String getDIAHORA() {
        return DIA + " " +HORA;
    }
    public String toString() {
        return DIA + " " +HORA;
    }
}