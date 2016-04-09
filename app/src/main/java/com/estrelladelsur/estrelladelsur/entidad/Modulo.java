package com.estrelladelsur.estrelladelsur.entidad;

public class Modulo {
    //MODULO
    private int ID_MODULO;
    private String MODULO;

       //ENTRENAMIENTO
    public Modulo(int id_modulo, String modulo) {
        ID_MODULO = id_modulo;
        MODULO = modulo;
    }

    public int getID_MODULO() {
        return ID_MODULO;
    }
    public String getMODULO() {
        return MODULO;
    }

}