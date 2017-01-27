package com.estrelladelsur.estrelladelsur.entidad;

public class Sancion {

    private int ID_SANCION;
    private int ID_JUGADOR;
    private int ID_DIVISION;
    private int ID_TORNEO;
    private int ID_ANIO;
    private int AMARILLA;
    private int ROJA;
    private int FECHA_SUSPENSION;
    private String NOMBRE_JUGADOR;
    private String DESCRIPCION_DIVISION;
    private String OBSERVACIONES;
    private String USUARIO_CREADOR;
    private String FECHA_CREACION;
    private String USUARIO_ACTUALIZACION;
    private String FECHA_ACTUALIZACION;
    private byte[] FOTO_JUGADOR;
    private String FOTO_JUGADOR_URL;

    // SANCION
    public Sancion(int id, int id_jugador, int torneo, int amarilla, int roja, int fecha, String observaciones, String usuario,
                   String fechaCreacion, String usuario_act, String fechaActualizacion) {

        ID_SANCION = id;
        ID_JUGADOR = id_jugador;
        ID_TORNEO = torneo;
        AMARILLA = amarilla;
        ROJA = roja;
        FECHA_SUSPENSION = fecha;
        OBSERVACIONES = observaciones;
        USUARIO_CREADOR = usuario;
        FECHA_CREACION = fechaCreacion;
        USUARIO_ACTUALIZACION = usuario_act;
        FECHA_ACTUALIZACION = fechaActualizacion;
    }

    // SANCION
    public Sancion(int id, int id_jugador, int torneo, int amarilla, int roja, int fecha, String observaciones) {

        ID_SANCION = id;
        ID_JUGADOR = id_jugador;
        ID_TORNEO = torneo;
        AMARILLA = amarilla;
        ROJA = roja;
        FECHA_SUSPENSION = fecha;
        OBSERVACIONES = observaciones;
    }


    // SANCION RECYCLER
    public Sancion(int id, int id_jugador, String nombre_jugador, byte[] foto,int id_division, String nombre_division,
                   int amarilla, int roja, int fecha, String observaciones) {

        ID_SANCION = id;
        ID_JUGADOR = id_jugador;
        NOMBRE_JUGADOR = nombre_jugador;
        FOTO_JUGADOR = foto;
        ID_DIVISION = id_division;
        DESCRIPCION_DIVISION = nombre_division;
        AMARILLA = amarilla;
        ROJA = roja;
        FECHA_SUSPENSION = fecha;
        OBSERVACIONES = observaciones;
    }
    public Sancion(int id, int id_jugador, String nombre_jugador, String foto, int id_division, String nombre_division,
                   int amarilla, int roja, int fecha, String observaciones) {

        ID_SANCION = id;
        ID_JUGADOR = id_jugador;
        NOMBRE_JUGADOR = nombre_jugador;
        FOTO_JUGADOR_URL = foto;
        ID_DIVISION = id_division;
        DESCRIPCION_DIVISION = nombre_division;
        AMARILLA = amarilla;
        ROJA = roja;
        FECHA_SUSPENSION = fecha;
        OBSERVACIONES = observaciones;
    }

    public byte[] getFOTO_JUGADOR() {
        return FOTO_JUGADOR;
    }

    public void setFOTO_JUGADOR(byte[] FOTO_JUGADOR) {
        this.FOTO_JUGADOR = FOTO_JUGADOR;
    }

    public int getID_DIVISION() {
        return ID_DIVISION;

    }

    public void setID_DIVISION(int ID_DIVISION) {
        this.ID_DIVISION = ID_DIVISION;
    }

    public String getNOMBRE_JUGADOR() {
        return NOMBRE_JUGADOR;
    }

    public void setNOMBRE_JUGADOR(String NOMBRE_JUGADOR) {
        this.NOMBRE_JUGADOR = NOMBRE_JUGADOR;
    }

    public String getDESCRIPCION_DIVISION() {
        return DESCRIPCION_DIVISION;
    }

    public void setDESCRIPCION_DIVISION(String DESCRIPCION_DIVISION) {
        this.DESCRIPCION_DIVISION = DESCRIPCION_DIVISION;
    }

    public int getID_SANCION() {
        return ID_SANCION;
    }

    public void setID_SANCION(int ID_SANCION) {
        this.ID_SANCION = ID_SANCION;
    }

    public int getID_JUGADOR() {
        return ID_JUGADOR;
    }

    public void setID_JUGADOR(int ID_JUGADOR) {
        this.ID_JUGADOR = ID_JUGADOR;
    }

    public int getAMARILLA() {
        return AMARILLA;
    }

    public void setAMARILLA(int AMARILLA) {
        this.AMARILLA = AMARILLA;
    }

    public int getROJA() {
        return ROJA;
    }

    public void setROJA(int ROJA) {
        this.ROJA = ROJA;
    }

    public int getFECHA_SUSPENSION() {
        return FECHA_SUSPENSION;
    }

    public void setFECHA_SUSPENSION(int FECHA_SUSPENSION) {
        this.FECHA_SUSPENSION = FECHA_SUSPENSION;
    }

    public String getOBSERVACIONES() {
        return OBSERVACIONES;
    }

    public void setOBSERVACIONES(String OBSERVACIONES) {
        this.OBSERVACIONES = OBSERVACIONES;
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

    public int getID_TORNEO() {
        return ID_TORNEO;
    }

    public int getID_ANIO() {
        return ID_ANIO;
    }

    public String getFOTO_JUGADOR_URL() {
        return FOTO_JUGADOR_URL;
    }

    public void setFOTO_JUGADOR_URL(String FOTO_JUGADOR_URL) {
        this.FOTO_JUGADOR_URL = FOTO_JUGADOR_URL;
    }
}