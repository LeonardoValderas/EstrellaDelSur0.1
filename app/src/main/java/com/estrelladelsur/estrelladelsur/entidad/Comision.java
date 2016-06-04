package com.estrelladelsur.estrelladelsur.entidad;

public class Comision {
    private int ID_COMISION;
    private String NOMBRE_COMISION;
    private byte[] FOTO_COMISION;
    private String NOMBRE_FOTO;
    private int ID_CARGO;
    private String CARGO;
    private String PERIODO_DESDE;
    private String PERIODO_HASTA;
    private String URL_COMISION;
    private String USUARIO_CREADOR;
    private String FECHA_CREACION;
    private String USUARIO_ACTUALIZACION;
    private String FECHA_ACTUALIZACION;

    public Comision(int id, String nombre, byte[] foto, String nombre_foto, int id_cargo,String cargo, String periodo_desde, String periodo_hasta, String url_comision, String usuario,
                    String fecha_creacion, String usuario_actualizacion, String fecha_actualizacion) {
        ID_COMISION = id;
        NOMBRE_COMISION = nombre;
        FOTO_COMISION = foto;
        NOMBRE_FOTO = nombre_foto;
        ID_CARGO = id_cargo;
        CARGO = cargo;
        PERIODO_DESDE = periodo_desde;
        PERIODO_HASTA = periodo_hasta;
        URL_COMISION = url_comision;
        USUARIO_CREADOR = usuario;
        FECHA_CREACION = fecha_creacion;
        USUARIO_ACTUALIZACION = usuario_actualizacion;
        FECHA_ACTUALIZACION = fecha_actualizacion;
    }
    public Comision(int id, String nombre, byte[] foto, String cargo, String periodo_desde, String periodo_hasta) {
        ID_COMISION = id;
        NOMBRE_COMISION = nombre;
        FOTO_COMISION = foto;
        CARGO = cargo;
        PERIODO_DESDE = periodo_desde;
        PERIODO_HASTA = periodo_hasta;
    }
    public String getCARGO() {
        return CARGO;
    }
    public int getID_CARGO() {
        return ID_CARGO;
    }
    public int getID_COMISION() {
        return ID_COMISION;
    }
    public String getNOMBRE_COMISION() {
        return NOMBRE_COMISION;
    }
    public byte[] getFOTO_COMISION() {
        return FOTO_COMISION;
    }
    public String getPERIODO_DESDE() {
        return PERIODO_DESDE;
    }
    public String getPERIODO_HASTA() {
        return PERIODO_HASTA;
    }
    public String getUSUARIO_CREADOR() {
        return USUARIO_CREADOR;
    }
    public String getFECHA_CREACION() {
        return FECHA_CREACION;
    }
    public String getUSUARIO_ACTUALIZACION() {
        return USUARIO_ACTUALIZACION;
    }
    public String getFECHA_ACTUALIZACION() {
        return FECHA_ACTUALIZACION;
    }
    public String getURL_COMISION() {return URL_COMISION;}
    public String getNOMBRE_FOTO() {return NOMBRE_FOTO; }
}