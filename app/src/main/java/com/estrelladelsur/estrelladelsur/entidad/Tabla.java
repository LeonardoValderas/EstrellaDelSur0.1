package com.estrelladelsur.estrelladelsur.entidad;

public class Tabla {
    private int ID_TABLA;
    private String TABLA;
    private String FECHA;

    public Tabla(int id, String tabla, String fecha) {
        ID_TABLA = id;
        TABLA = tabla;
        FECHA = fecha;
    }

    public int getID_TABLA() {
        return ID_TABLA;
    }

    public void setID_TABLA(int ID_TABLA) {
        this.ID_TABLA = ID_TABLA;
    }

    public String getTABLA() {
        return TABLA;
    }

    public void setTABLA(String TABLA) {
        this.TABLA = TABLA;
    }

    public String getFECHA() {
        return FECHA;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }
}