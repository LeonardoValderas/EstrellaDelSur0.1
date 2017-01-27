package com.estrelladelsur.estrelladelsur.entidad;

public class Direccion {
    private int ID_DIRECCION;
    private String NOMBRE_DIRECCION;
    private byte[] FOTO_DIRECCION;
    private String NOMBRE_FOTO;
    private int ID_CARGO;
    private String CARGO;
    private String PERIODO_DESDE;
    private String PERIODO_HASTA;
    private String URL_DIRECCION;
    private String USUARIO_CREADOR;
    private String FECHA_CREACION;
    private String USUARIO_ACTUALIZACION;
    private String FECHA_ACTUALIZACION;

    public Direccion(int id, String nombre, byte[] foto, String nombre_foto, int id_cargo, String cargo, String periodo_desde, String periodo_hasta,
                     String url_direccion, String usuario, String fecha_creacion, String usuario_actualizacion, String fecha_actualizacion) {
        ID_DIRECCION = id;
        NOMBRE_DIRECCION = nombre;
        FOTO_DIRECCION = foto;
        NOMBRE_FOTO = nombre_foto;
        ID_CARGO = id_cargo;
        CARGO = cargo;
        PERIODO_DESDE = periodo_desde;
        PERIODO_HASTA = periodo_hasta;
        URL_DIRECCION = url_direccion;
        USUARIO_CREADOR = usuario;
        FECHA_CREACION = fecha_creacion;
        USUARIO_ACTUALIZACION = usuario_actualizacion;
        FECHA_ACTUALIZACION = fecha_actualizacion;
    }
    //USUARIO
    public Direccion(int id, String nombre, byte[] foto, String cargo, String periodo_desde, String periodo_hasta) {
        ID_DIRECCION = id;
        NOMBRE_DIRECCION = nombre;
        FOTO_DIRECCION = foto;
        CARGO = cargo;
        PERIODO_DESDE = periodo_desde;
        PERIODO_HASTA = periodo_hasta;
    }
    public Direccion(int id, String nombre, String url_direccion, String cargo, String periodo_desde, String periodo_hasta) {
        ID_DIRECCION = id;
        NOMBRE_DIRECCION = nombre;
        URL_DIRECCION = url_direccion;
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
    public int getID_DIRECCION() {
        return ID_DIRECCION;
    }
    public String getNOMBRE_DIRECCION() {
        return NOMBRE_DIRECCION;
    }
    public byte[] getFOTO_DIRECCION() {
        return FOTO_DIRECCION;
    }
    public String getPERIODO_DESDE() {
        return PERIODO_DESDE;
    }
    public String getPERIODO_HASTA() {
        return PERIODO_HASTA;
    }
    public String getNOMBRE_FOTO() { return NOMBRE_FOTO; }
    public String getURL_DIRECCION() {return URL_DIRECCION; }
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

}