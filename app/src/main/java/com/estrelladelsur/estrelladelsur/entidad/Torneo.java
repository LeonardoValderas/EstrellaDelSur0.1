package com.estrelladelsur.estrelladelsur.entidad;

public class Torneo {

    private int ID_TORNEO;
    private int ID_TORNEO_ACTUAL;
    private Boolean ACTUAL;
    private Boolean ISACTUAL;
    private Boolean ISACTUAL_ANTERIOR;
    private String DESCRIPCION;
    private String USUARIO_CREADOR;
    private String FECHA_CREACION;
    private String USUARIO_ACTUALIZACION;
    private String FECHA_ACTUALIZACION;
    private int FECHA_ANIO;

    public Torneo(int id, String descripcion, boolean actual, String usuario,
                  String fechaCreacion, String usuario_act, String fechaActualizacion) {

        ID_TORNEO = id;
        DESCRIPCION = descripcion;
        ACTUAL = actual;
        USUARIO_CREADOR = usuario;
        FECHA_CREACION = fechaCreacion;
        USUARIO_ACTUALIZACION = usuario_act;
        FECHA_ACTUALIZACION = fechaActualizacion;
    }

    public Torneo(int id, String descripcion, boolean actual, boolean actual_anterior, int anio, String usuario,
                  String fechaCreacion, String usuario_act, String fechaActualizacion) {

        ID_TORNEO = id;
        DESCRIPCION = descripcion;
        ACTUAL = actual;
        ISACTUAL_ANTERIOR = actual_anterior;
        FECHA_ANIO = anio;
        USUARIO_CREADOR = usuario;
        FECHA_CREACION = fechaCreacion;
        USUARIO_ACTUALIZACION = usuario_act;
        FECHA_ACTUALIZACION = fechaActualizacion;
    }

    public Torneo(int id, int id_torneo, int fecha_anio, boolean isActual) {
        ID_TORNEO_ACTUAL = id;
        ID_TORNEO = id_torneo;
        FECHA_ANIO = fecha_anio;
        ISACTUAL = isActual;

    }
    public Torneo(int id, String descripcion) {

        ID_TORNEO = id;
        DESCRIPCION = descripcion;
    }

    public int getID_TORNEO() {
        return ID_TORNEO;
    }

    public String getUSUARIO_CREADOR() {
        return USUARIO_CREADOR;
    }

    public void setUSUARIO_CREADOR(String USUARIO_CREADOR) {
        this.USUARIO_CREADOR = USUARIO_CREADOR;
    }

    public String getUSUARIO_ACTUALIZACION() {
        return USUARIO_ACTUALIZACION;
    }

    public void setUSUARIO_ACTUALIZACION(String USUARIO_ACTUALIZACION) {
        this.USUARIO_ACTUALIZACION = USUARIO_ACTUALIZACION;
    }

    public void setID_TORNEO(int iD_TORNEO) {
        ID_TORNEO = iD_TORNEO;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public String toString() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String dESCRIPCION) {
        DESCRIPCION = dESCRIPCION;
    }

    public String getFECHA_CREACION() {
        return FECHA_CREACION;
    }

    public void setFECHA_CREACION(String fECHA_CREACION) {
        FECHA_CREACION = fECHA_CREACION;
    }

    public String getFECHA_ACTUALIZACION() {
        return FECHA_ACTUALIZACION;
    }

    public void setFECHA_ACTUALIZACION(String fECHA_ACTUALIZACION) {
        FECHA_ACTUALIZACION = fECHA_ACTUALIZACION;
    }

    public int getID_TORNEO_ACTUAL() {
        return ID_TORNEO_ACTUAL;
    }

    public void setID_TORNEO_ACTUAL(int ID_TORNEO_ACTUAL) {
        this.ID_TORNEO_ACTUAL = ID_TORNEO_ACTUAL;
    }

    public int getFECHA_ANIO() {
        return FECHA_ANIO;
    }

    public void setFECHA_ANIO(int FECHA_ANIO) {
        this.FECHA_ANIO = FECHA_ANIO;
    }

    public Boolean getACTUAL() {
        return ACTUAL;
    }

    public void setACTUAL(Boolean ACTUAL) {
        this.ACTUAL = ACTUAL;
    }

    public Boolean getISACTUAL() {
        return ISACTUAL;
    }

    public Boolean getISACTUAL_ANTERIOR() {
        return ISACTUAL_ANTERIOR;
    }
}