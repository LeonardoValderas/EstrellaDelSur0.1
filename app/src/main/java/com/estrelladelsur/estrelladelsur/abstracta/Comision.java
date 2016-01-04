package com.estrelladelsur.estrelladelsur.abstracta;

public class Comision {

    private int ID_COMISION;
    private String NOMBRE_COMISION;
    private byte[] FOTO_COMISION;
    private int ID_CARGO;
    private String CARGO;
    private String PERIODO_DESDE;
    private String PERIODO_HASTA;
    private String USUARIO_CREADOR;
    private String FECHA_CREACION;
    private String USUARIO_ACTUALIZACION;
    private String FECHA_ACTUALIZACION;


    public Comision(int id, String nombre, byte[] foto, int id_cargo,String cargo, String periodo_desde, String periodo_hasta,String usuario,
                    String fecha_creacion, String usuario_actualizacion, String fecha_actualizacion) {

        ID_COMISION = id;
        NOMBRE_COMISION = nombre;
        FOTO_COMISION = foto;
        ID_CARGO = id_cargo;
        CARGO = cargo;
        PERIODO_DESDE = periodo_desde;
        PERIODO_HASTA = periodo_hasta;
        USUARIO_CREADOR = usuario;
        FECHA_CREACION = fecha_creacion;
        USUARIO_ACTUALIZACION = usuario_actualizacion;
        FECHA_ACTUALIZACION = fecha_actualizacion;
    }

    public String getCARGO() {
        return CARGO;
    }

    public void setCARGO(String CARGO) {
        this.CARGO = CARGO;
    }

    public int getID_CARGO() {
        return ID_CARGO;
    }

    public void setID_CARGO(int ID_CARGO) {
        this.ID_CARGO = ID_CARGO;
    }

    public int getID_COMISION() {
        return ID_COMISION;
    }

    public void setID_COMISION(int ID_COMISION) {
        this.ID_COMISION = ID_COMISION;
    }

    public String getNOMBRE_COMISION() {
        return NOMBRE_COMISION;
    }

    public void setNOMBRE_COMISION(String NOMBRE_COMISION) {
        this.NOMBRE_COMISION = NOMBRE_COMISION;
    }

    public byte[] getFOTO_COMISION() {
        return FOTO_COMISION;
    }

    public void setFOTO_COMISION(byte[] FOTO_COMISION) {
        this.FOTO_COMISION = FOTO_COMISION;
    }

    public String getPERIODO_DESDE() {
        return PERIODO_DESDE;
    }

    public void setPERIODO_DESDE(String PERIODO_DESDE) {
        this.PERIODO_DESDE = PERIODO_DESDE;
    }

    public String getPERIODO_HASTA() {
        return PERIODO_HASTA;
    }

    public void setPERIODO_HASTA(String PERIODO_HASTA) {
        this.PERIODO_HASTA = PERIODO_HASTA;
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
}