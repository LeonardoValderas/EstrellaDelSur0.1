package com.estrelladelsur.estrelladelsur.entidad;

import java.util.ArrayList;

public class EntrenamientoRecycler {

    private int ID_ENTRENAMIENTO;
    private int ID_CANCHA;
    private String NOMBRE;
    private String DIA;
    private String HORA;
    private ArrayList<Entrenamiento_Division> ENTRENAMIENTO_DIVISION;
    //private int ID_DIVISION;
    //private String DESCRIPCION;


    public EntrenamientoRecycler(int id, String dia, String hora, int id_cancha, String nombre, ArrayList<Entrenamiento_Division> entrenamiento_division) {
        ID_ENTRENAMIENTO = id;
        DIA = dia;
        HORA = hora;
        ID_CANCHA = id_cancha;
        NOMBRE = nombre;
        ENTRENAMIENTO_DIVISION = entrenamiento_division;
    }

    public ArrayList<Entrenamiento_Division> getENTRENAMIENTO_DIVISION() {
        return ENTRENAMIENTO_DIVISION;
    }

    public void setENTRENAMIENTO_DIVISION(ArrayList<Entrenamiento_Division> ENTRENAMIENTO_DIVISION) {
        this.ENTRENAMIENTO_DIVISION = ENTRENAMIENTO_DIVISION;
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
    public String getNOMBRE() {
        return NOMBRE;
    }
    public void setNOMBRE(String nOMBRE) {
        NOMBRE = nOMBRE;
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